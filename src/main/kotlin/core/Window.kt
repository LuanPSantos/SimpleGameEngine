package core

import core.input.GameKeyListener
import core.input.GameMouseListener
import java.awt.BorderLayout
import java.awt.Canvas
import java.awt.Dimension
import java.awt.Graphics
import java.awt.image.BufferStrategy
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.awt.image.DataBufferInt
import javax.swing.JFrame

class Window(
    width: Int,
    height: Int,
    scale: Double,
    title: String = "Simple Game Engine"
) : JFrame(title) {

    private val bufferedImage = BufferedImage(width, height, TYPE_INT_RGB)
    private val bufferStrategy: BufferStrategy
    private val graphics: Graphics
    private val canvas: Canvas
    val screen = Screen(width, height, (bufferedImage.raster.dataBuffer as DataBufferInt).data, scale)

    init {
        val dimension = Dimension((screen.width * screen.scale).toInt(), (screen.height * screen.scale).toInt())

        canvas = Canvas()
        canvas.preferredSize = dimension
        canvas.maximumSize = dimension
        canvas.minimumSize = dimension

        defaultCloseOperation = EXIT_ON_CLOSE
        layout = BorderLayout()
        add(canvas, BorderLayout.CENTER)
        pack()
        setLocationRelativeTo(null)
        isResizable = false
        isVisible = true

        canvas.createBufferStrategy(NUMBER_OF_BUFFERS)
        bufferStrategy = canvas.bufferStrategy
        graphics = bufferStrategy.drawGraphics
    }

    fun update(drawPixelsOn: (Screen) -> Unit) {
        screen.clear()
        drawPixelsOn(screen)

        graphics.drawImage(bufferedImage, START_X, START_Y, canvas.width, canvas.height, null)
        bufferStrategy.show()
    }

    fun addCanvasKeyListener(keyListener: GameKeyListener) {
        canvas.addKeyListener(keyListener)
    }

    fun addCanvasMouseListener(mouseListener: GameMouseListener) {
        canvas.addMouseListener(mouseListener)
        canvas.addMouseMotionListener(mouseListener)
    }

    companion object {
        const val NUMBER_OF_BUFFERS = 2
        const val START_X = 0
        const val START_Y = 0
    }

    data class Screen(
        val width: Int,
        val height: Int,
        private var pixels: IntArray = intArrayOf(),
        val scale: Double = 1.0
    ) {
        fun clear() {
            for (y in 0..<height) {
                for (x in 0..<width) {
                    pixels[x + y * width] = 0xFF000000.toInt()
                }
            }
        }

        fun setPixelAt(x: Int, y: Int, pixelValue: Int) {
            pixels[x + y * width] = pixelValue
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
}