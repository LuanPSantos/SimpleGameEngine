package core.render

import core.Screen
import core.math.Vector2
import javax.imageio.ImageIO
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.round

class Sprite(
    val width: Int,
    val height: Int,
    var position: Vector2<Int> = Vector2(0,0),
    var scale: Vector2<Float> = Vector2(1f,1f),
    val pixels: Array<IntArray> = Array(height) { IntArray(width) },
) : Renderable, Cloneable {

    override fun render(screen: Screen) {
        if (position.x < -width) return
        if (position.y < -height) return
        if (position.x >= screen.width) return
        if (position.y >= screen.height) return

        var newX = 0
        var newY = 0
        var newWidth = round(width * abs(scale.x) ).toInt()
        var newHeight = round(height * abs(scale.y)).toInt()

        if (position.x < 0) {
            newX -= position.x
        }

        if (position.y < 0) {
            newY -= position.y
        }

        if (newWidth + position.x > screen.width) {
            newWidth -= newWidth + position.x - screen.width
        }

        if (newHeight + position.y > screen.height) {
            newHeight -= newHeight + position.y - screen.height
        }

        for (y in newY..<newHeight) {
            for (x in newX..<newWidth) {
                val pixelPosition = Vector2(
                    position.x + x,
                    position.y + y
                )

                val scaledX = applyScale(x, width, newWidth, scale.x)
                val scaledY = applyScale(y, height, newHeight, scale.y)

                screen.setPixelAt(pixels[scaledY][scaledX], pixelPosition)
            }
        }
    }

    public override fun clone(): Sprite {
        return Sprite(
            width,
            height,
            Vector2(position.x, position.y),
            Vector2(scale.x, scale.y),
            pixels
        )
    }

    private fun applyScale(index: Int, originalSize: Int, newSize: Int, scaleFactor: Float): Int {
        var scaled = floor(index * (originalSize.toFloat() / newSize.toFloat())).toInt() % pixels.size

        if(scaleFactor < 0) {
            scaled = originalSize - 1 - scaled
        }

        return scaled
    }

    companion object {
        fun loadImage(path: String): Sprite {
            val bufferedSprite = ImageIO.read(Sprite::class.java.getResourceAsStream(path))
            val width = bufferedSprite.width
            val height = bufferedSprite.height
            val pixels = bufferedSprite.getRGB(
                ZERO, ZERO,
                width, height,
                NULL, ZERO, width
            )

            val sprite = Sprite(width, height, Vector2(ZERO, ZERO))
            for (y in 0..<height) {
                for (x in 0..<width) {
                    sprite.pixels[y][x] = pixels[x + y * width]
                }
            }

            bufferedSprite.flush()

            return sprite
        }

        private const val ZERO = 0
        private val NULL = null
        const val PINK = 0xFFFF00FF.toInt()
    }
}