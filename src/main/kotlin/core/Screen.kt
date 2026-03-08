package core

import core.math.Vector2
import core.render.Image
import java.awt.image.BufferedImage
import java.awt.image.DataBufferInt

class Screen(
    val width: Int,
    val height: Int,
    val scale: Double = 1.0
) {

    val bufferedImage: BufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    private val pixels: IntArray = (bufferedImage.raster.dataBuffer as DataBufferInt).data

    fun clear() {
        for (y in 0..<height) {
            for (x in 0..<width) {
                pixels[x + y * width] = 0xFF000000.toInt()
            }
        }
    }

    fun setPixelAt(pixel: Int, position: Vector2<Int>) {
        if (position.x < 0 || position.x >= width
            || position.y < 0 || position.y >= height
            || pixel == Image.PINK
        ) return

        pixels[position.x + position.y * width] = pixel
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Screen

        if (width != other.width) return false
        if (height != other.height) return false
        if (!pixels.contentEquals(other.pixels)) return false
        if (scale != other.scale) return false

        return true
    }

    override fun hashCode(): Int {
        var result = width
        result = 31 * result + height
        result = 31 * result + pixels.contentHashCode()
        result = 31 * result + scale.hashCode()
        return result
    }
}