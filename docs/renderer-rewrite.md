# Renderer Rewrite — Forward (scatter) → Inverse (gather) Mapping

> Study notes for changing the rasterizer's direction.
> Started 2026-05-31 / 2026-06-01. This is a **study project** — the goal is to
> understand the math and implement it myself. These notes record what we
> derived together, not finished code.

## The one idea everything hangs on

> A renderer is just a function that maps between coordinate spaces. The only
> real question is **which direction you evaluate that function.**

- **Current engine = forward mapping (scatter):** loop over the *source*
  (sprite texels), push each through the transform, write where it lands.
- **Goal = inverse mapping (gather):** loop over the *destination* (screen
  pixels), pull each back through the inverse transform, sample whatever world
  point is there. ("Scan the screen to know what's in the world there.")

### Why forward mapping makes zoom buggy (structural, not a tuning bug)

The transform includes a scale by `zoom`, so it is **not** a 1-to-1 map between
the two integer pixel grids:

- **Magnify (`zoom > 1`):** adjacent source texels land >1 screen pixel apart →
  untouched pixels between them → **holes / gaps**.
- **Minify (`zoom < 1`):** many source texels collapse onto one screen pixel →
  **overdraw / shimmer**.

Inverse mapping steps by exactly 1 over the **destination** grid, so every
screen pixel is written exactly once — no holes, no overdraw. Zoom becomes a
single clean factor inside one inverse function.

(Classic result from image warping — for resampling under a scale, always
iterate the destination and invert. Wolberg, *Digital Image Warping*.)

Extra bug in the current code specifically: the loop bound `objectIntScaled` is
in **screen pixels** (it has `* zoom`), but inside the loop the index is added
to the object's **world position** and re-multiplied by zoom in
`worldToScreenPosition` → on-screen size scales like `zoom²`. See
`core/render/Camera.kt:46` (`capture`) and `:79` (`worldToScreenPosition`).

## Coordinate spaces

| Space | Units | Origin | In code |
|---|---|---|---|
| Local / texture | texels | top-left of sprite | `Image.pixels[y][x]`, `[0,texW)×[0,texH)` |
| World | world units (`Double`) | wherever | `GameObject.transform.position` / `scale` |
| Screen | pixels (`Int`) | top-left of window | `Screen.pixels`, `[0,W)×[0,H)` |

Transform chain: `texel --(model: pos, scale)--> world --(camera: pos, zoom)--> screen`.
Each link is an invertible affine map. (Storing these as 3×3 matrices later
would make translation a multiplication and let us invert once instead of
hand-deriving — a good future refactor.)

## The math we derived (all verified)

Running example: object `p = (100,50)`, `s = (20,20)` (world size), texture `4×4`.

### Camera: world ↔ screen

```
worldToScreen :  sx = ( wx − cam.x) · zoom
                 sy = (−wy − cam.y) · zoom        # −wy: world-up is +y, screen-y is down

screenToWorld :  wx =  sx / zoom + cam.x          # (the inverse — the keystone)
                 wy = −(sy / zoom + cam.y)
```

### Model: texel ↔ world

```
texel → world :  world.x =  p.x + (u/texW) · s.x
                 world.y = (p.y + s.y) − (v/texH) · s.y     # flip lives here

world → texel :  u = (wx − p.x) / s.x · texW
                 v = ((p.y + s.y) − wy) / s.y · texH
```

The renderer evaluates the **inverse** directions (`screenToWorld`,
`world → texel`) inside the gather loop.

### The flip principle (Y reflection)

A *flip* (minus sign) appears whenever two spaces disagree about which way is
"down". A *flip* is **not** the same as an *inversion* (`T → T⁻¹`); inverting a
map neither creates nor removes a flip — it carries through automatically.

Count flips along `texture → world → screen`:

- texture-y grows **down**, screen-y grows **down** → they *agree* → want an
  **even** number of flips so they cancel → upright sprite.
- `world → screen` (camera) disagrees with both → **1 flip**.
- therefore `texel → world` must add a **2nd flip** → 2 flips cancel → upright.

That's why `texel → world` has the `(p.y + s.y) − …` form. (A no-flip
`p.y + (v/texH)·s.y` map renders the sprite upside down once the camera flips.)

### Containment test

`screenToWorld` returns a world point for *every* screen pixel — including empty
space and other objects. Blindly running `world → texel` on a point off the
sprite gives an out-of-range `(u,v)` → `getPixelAt` throws
`ArrayIndexOutOfBoundsException`.

Rule: the point is on the sprite **iff `0 ≤ u < texW` and `0 ≤ v < texH`**.
Otherwise skip it and try the next object.

## Chosen architecture: Option B — per-object AABB scan

Two valid loop shapes were considered:

- **A — full-screen scan:** scene loops *every* screen pixel; inner loop over
  objects; topmost opaque wins. Simple, correct, but `O(W·H·N)`.
- **B — per-object AABB scan (chosen, = what GPUs do):** keep
  `GameScene.render()` doing `objects.forEach { camera.capture(it) }`. Invert
  the **body** of `capture`: compute the sprite's screen-space bounding box
  (forward-project its corners), then gather-loop only those pixels. Draw order
  = depth (painter's algorithm). Cheap; barely disturbs current structure.

Two jobs that coincide for an *unrotated* sprite (they separate once rotation
exists):
- `(u,v)` range check = **correctness** (stops the exception, says who owns a pixel).
- screen AABB = **efficiency** (don't even visit pixels outside the sprite).

Target shape of the new `capture`:

```
compute the sprite's screen rectangle  →  loop those screen pixels
   for each pixel: screenToWorld → world→texel → getPixelAt → setPixel
```

## Where we stopped — NEXT STEP

**Compute the screen-space AABB (loop bounds) from the world corners.**
We have the sprite's world corners: bottom-left `p = (100,50)` and top-right
`p+s = (120,70)`. Turn them into `min/max sx, min/max sy` via `worldToScreen`.

⚠️ Watch the **y-flip**: `worldToScreen` sends *larger* world-y *higher* (smaller
sy), so the world bottom-left corner does **not** map to the screen-rectangle's
top. Take `min`/`max` of the projected values, don't assume corner order. Clamp
the result to `[0,W) × [0,H)`.

## Remaining TODO (after AABB bounds)

1. **Depth / "what's above me"** for overlapping objects — painter's order via
   draw order, first opaque hit wins. Preserve the **magenta color-key**
   (`Image.PINK`, `Screen.setPixelAt` `core/Screen.kt:28`) as the
   "transparent, keep looking deeper" signal.
2. **Implement in Kotlin:** add `screenToWorld` to `Camera`; rewrite the body of
   `Camera.capture` to gather over the screen AABB; decide where `world→texel`
   lives (on `GameObject`/`Renderable`, since it's the object's own transform).
3. **Delete** the forward-scatter loop (`Camera.kt:58–75`); **relocate** the old
   culling (`Camera.kt:53–56`) — its purpose becomes the AABB / containment test.
4. **Cleanups spotted:** dead import `javax.swing.Spring.scale` (`Camera.kt:8`),
   unused `java.awt.Color`; the culling `return` sits inside `graphics.forEach {}`
   which is a non-local return from `capture()` (latent bug with >1 renderable).

## Things the current engine already gets right (keep)

- Fixed-timestep accumulator loop (`core/GameLoop.kt:33`) — textbook. (Could add
  render interpolation later for smoothness, but the structure is correct.)
- The camera world↔screen transform math itself is standard and correct — the
  bug was only in how it was *used* (scatter instead of gather).
- Magenta color-key transparency trick (used cleverly by `CartesianPlane.Grid`).
