package game

import core.math.Position
import core.render.Renderable
import javax.imageio.ImageIO

class Sprite private constructor(
    pixels: IntArray,
    width: Int,
    height: Int,
    position: Position
) : Renderable(pixels, width, height, position) {

    companion object {
        fun loadImage(path: String): Sprite {
            val bufferedSprite = ImageIO.read(Sprite::class.java.getResourceAsStream(path))
            val width = bufferedSprite.width
            val height = bufferedSprite.height
            val sprite = Sprite(
                bufferedSprite.getRGB(
                    ZERO, ZERO,
                    width, height,
                    NULL,
                    ZERO,
                    width
                ),
                width,
                height,
                Position(ZERO, ZERO)
            )
            bufferedSprite.flush()

            return sprite
        }

        private const val ZERO = 0
        private val NULL = null
    }
}