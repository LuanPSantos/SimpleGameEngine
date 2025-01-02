package core.render

import javax.imageio.ImageIO

class Image(
    width: Int,
    height: Int,
    val pixels: Array<IntArray> = Array(height) { IntArray(width) },
) : Renderable(width, height), Cloneable {

    override fun getPixelAt(x: Int, y: Int): Int {
        return pixels[y][x]
    }

    public override fun clone(): Image {
        return Image(
            width,
            height,
            pixels
        )
    }

    companion object {
        fun loadImage(path: String): Image {
            val bufferedImage = ImageIO.read(Image::class.java.getResourceAsStream(path))
            val width = bufferedImage.width
            val height = bufferedImage.height
            val pixels = bufferedImage.getRGB(
                ZERO, ZERO,
                width, height,
                NULL, ZERO, width
            )

            val image = Image(width, height)
            for (y in 0..<height) {
                for (x in 0..<width) {
                    image.pixels[y][x] = pixels[x + y * width]
                }
            }

            bufferedImage.flush()

            return image
        }

        private const val ZERO = 0
        private val NULL = null
        const val PINK = 0xFFFF00FF.toInt()
    }
}