package game

import core.Window
import core.math.Position
import core.render.Renderable
import javax.imageio.ImageIO

class Sprite(
    val width: Int,
    val height: Int,
    var position: Position = Position(),
    val pixels: Array<IntArray> = Array(height) { IntArray(width) },
) : Renderable {

    override fun render(screen: Window.Screen) {
        if (position.x < -width) return
        if (position.y < -height) return
        if (position.x >= screen.width) return
        if (position.y >= screen.height) return

        var newX = 0
        var newY = 0
        var newWidth = width
        var newHeight = height

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
                val pixelPosition = Position(
                    position.x + x,
                    position.y + y
                )
                Renderable.setPixelOnScreen(pixels[y][x], pixelPosition, screen)
            }
        }
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

            val sprite = Sprite(width, height, Position(ZERO, ZERO))
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