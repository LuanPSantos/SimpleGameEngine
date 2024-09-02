package core

import core.input.GameMouseMotionListener
import java.awt.BorderLayout
import java.awt.Canvas
import java.awt.Dimension
import java.awt.Graphics
import java.awt.event.KeyListener
import java.awt.event.MouseListener
import java.awt.image.BufferStrategy
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.awt.image.DataBufferInt
import javax.swing.JFrame

class Window(
    val screen: Screen,
    title: String = "Simple Game Engine"
) : JFrame(title) {

    private val bufferedImage = BufferedImage(screen.width, screen.height, TYPE_INT_RGB)
    private val bufferStrategy: BufferStrategy
    private val graphics: Graphics
    private val canvas: Canvas

    init {
        screen.pixels = (bufferedImage.raster.dataBuffer as DataBufferInt).data

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

        clear()
        drawPixelsOn(screen)

        graphics.drawImage(bufferedImage, START_X, START_Y, canvas.width, canvas.height, null)
        bufferStrategy.show()
    }

    fun addCanvasKeyListener(keyListener: KeyListener) {
        canvas.addKeyListener(keyListener)
    }

    fun addCanvasMouseListener(mouseListener: MouseListener) {
        canvas.addMouseListener(mouseListener)
    }

    fun addCanvasMouseMotionListener(mouseMotionListener: GameMouseMotionListener) {
        canvas.addMouseMotionListener(mouseMotionListener)
    }

    private fun clear() {
        for (index in screen.pixels.indices) {
            screen.pixels[index] = 0xFF0000FF.toInt()
        }
    }

    companion object {
        const val NUMBER_OF_BUFFERS = 2
        const val START_X = 0
        const val START_Y = 0
    }

    data class Screen(
        val width: Int,
        val height: Int,
        var pixels: IntArray = intArrayOf(),
        val scale: Double = 1.0
    ) {
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