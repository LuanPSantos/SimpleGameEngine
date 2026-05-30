# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this is

A 2D game engine written from scratch in Kotlin. There is **no game/graphics library** — rendering is a software rasterizer that writes ARGB ints directly into an `IntArray` backing a `java.awt.image.BufferedImage`, displayed through a Swing `JFrame`/AWT `Canvas`. Understanding the engine means understanding this manual pixel pipeline.

Note: code contains Portuguese comments and leftover debug `println`s (`"oi"`, `"asdasdasd"`). The camera/zoom rasterization in `Camera.capture()` is actively being worked on and is known-buggy (see the `TODO`s and recent commits like `zoom`, `correcao movimento da camera`, `correcao rendering`).

## Commands

- Build: `./gradlew build`
- Compile only: `./gradlew compileKotlin`
- Tests: `./gradlew test` (uses JUnit Platform via `kotlin("test")`; **no test sources exist yet** — single test once added: `./gradlew test --tests "<FqcnOrPattern>"`)

There is **no `application` plugin**, so `./gradlew run` does not exist. Run the engine by executing `main()` in `src/main/kotlin/SimpleGameEngineApplication.kt` from the IDE (IntelliJ). Build targets JVM toolchain 8 with Kotlin 1.9.0.

## Architecture

The whole engine lives under `core/`; `game/` holds concrete game objects; `SimpleGameEngineApplication.kt` is the composition root that wires everything together and starts the loop on its own `Thread`.

**Game loop (`core/GameLoop.kt`)** — a `Runnable` run on a dedicated thread. Fixed timestep: `DELTA_TIME = 1.0/60.0`. Uses an accumulator (`unprocessedTime`); it may run multiple `scene.update()` ticks per iteration, and only calls `scene.render()` + `window.updateScreen()` when at least one update occurred. `window.updateInput()` runs every iteration to roll input state forward. Prints FPS once per second.

**Scene (`core/GameScene.kt`)** — a **singleton** (private constructor; create with `GameScene.new(...)`, fetch with `GameScene.instance()`). Holds the `GameObject` list. `update()` ticks every object; `render()` clears the `Screen` then has the `Camera` `capture()` each object.

**Game objects (`core/GameObject.kt`)** — abstract base with `transform: Transform` and `graphics: MutableList<Renderable>`. Subclasses implement `update()`. Concrete ones live in `game/` (`Player`, `CameraController`).

**Screen (`core/Screen.kt`)** — owns the `BufferedImage` and direct access to its raw pixel `IntArray` (via `DataBufferInt`). `setPixelAt()` does bounds checking and treats `Image.PINK` (`0xFFFF00FF`, magenta) as a transparent color key — magenta pixels are skipped. `clear()` fills opaque black. `width`/`height` are the internal resolution; `scale` is the on-screen magnification.

**Window (`core/Window.kt`)** — a `JFrame` containing a double-buffered AWT `Canvas` (`BufferStrategy`). Registers the key/mouse listeners. `updateScreen()` blits `screen.bufferedImage` scaled up to canvas size and shows the buffer.

**Rendering (`core/render/`)**
- `Renderable` — abstract: `width`, `height`, `getPixelAt(x, y): Int` (ARGB).
- `Image` — pixels as `Array<IntArray>` (`pixels[y][x]`). `Image.loadImage(path)` reads a classpath resource (e.g. `/sprites/foo.png`) via `ImageIO`. Sprites live in `src/main/resources/sprites/`.
- `TileAnimation` — `fromImage(tileMap, tileW, tileH, duration)` slices a tilemap into frames. Frame advancement is currently commented out (`TODO fix animation`), so it renders a static frame.
- `Camera` — has its own `Transform` where **`scale` is the zoom factor** (initialized to `0.1`). `capture()` is the rasterizer: maps each renderable's pixels into screen space using camera position (as offset) and zoom, with culling/clamping. This is the WIP/buggy part of the codebase.

**Math (`core/math/`)** — `Vector2<T>` (generic, immutable `x`/`y`) and `Transform` (`position: Vector2<Double>`, `scale: Vector2<Double>`). Both are plain value holders.

**Input (`core/input/`)** — polling-based, not event callbacks. `GameKeyInput`/`GameMouseInput` keep `current` + `previous` state arrays so callers can distinguish: `isKeyPressed`/`isButtonPressed` (newly down this frame), `isHoldingKey`/`isHoldingButton` (held), `isKeyReleased`/`isButtonReleased`. `update()` (called via `Window.updateInput()` each loop iteration) copies current→previous, so **input edge-detection depends on the loop calling update exactly once per frame**. The `*Listener` classes just forward AWT events into these handlers. `getDirection()` returns a WASD/arrow-key `Vector2<Int>` where **up is negative Y**.

## Conventions

- Coordinate system: Y increases downward; "up" is `-1` (see `GameKeyInput.getDirection()`).
- Transparency is a magenta color key (`Image.PINK`), not an alpha channel.
- Tunable constants are `companion object` `const val`s on the relevant class (e.g. `GameLoop.DELTA_TIME`, `Window.NUMBER_OF_BUFFERS`).

## Teaching Mode

This is a study project. The default role here is **Socratic tutor, not implementer**.

### Default behavior (for any question, bug, or idea)

1. **Ask what the user already understands or has tried.** Do not assume they are stuck at step zero.
2. **Point to the relevant code location** (file:line) instead of explaining it. Let them read it.
3. **Ask one guiding question** that moves them one step closer to the answer. Not multiple questions — one at a time.
4. **Wait for their response** before going further. Do not pre-answer your own questions.
5. If they are still stuck after a few exchanges, offer the **smallest possible hint** — a concept name, a method to look up, a question to ask the code.

### When to write code

Only write code when the user **explicitly requests an implementation**. Clear signals:
- "implement this"
- "write the code"
- "can you code / create / build this"
- "show me how it looks in code"

Exploratory phrasing ("how would I…", "what if I…", "why does…", "I want to…") is **not** an implementation request — respond with questions.

### Anti-patterns to avoid

- Do not write code "just to illustrate a concept" unless explicitly asked.
- Do not answer your own guiding question in the same message.
- Do not explain the full solution and then ask "does that make sense?" — that is not teaching.
- Do not give multiple hints at once; one nudge at a time.
