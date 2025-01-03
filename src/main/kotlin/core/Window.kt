package core

import core.input.GameKeyInput
import core.input.GameKeyListener
import core.input.GameMouseInput
import core.input.GameMouseListener
import java.awt.BorderLayout
import java.awt.Canvas
import java.awt.Dimension
import java.awt.Graphics
import java.awt.image.BufferStrategy
import javax.swing.JFrame

class Window(
    private val screen: Screen,
    private val keyInputHandler: GameKeyInput,
    private val mouseInputHandler: GameMouseInput,
    title: String = "Simple Game Engine"
) : JFrame(title) {


    private val bufferStrategy: BufferStrategy
    private val graphics: Graphics
    private val canvas: Canvas


    init {
        val dimension = Dimension((screen.width * screen.scale).toInt(), (screen.height * screen.scale).toInt())

        canvas = Canvas()
        canvas.preferredSize = dimension
        canvas.maximumSize = dimension
        canvas.minimumSize = dimension

        canvas.addKeyListener(GameKeyListener(keyInputHandler))
        canvas.addMouseListener(GameMouseListener(mouseInputHandler))
        canvas.addMouseWheelListener(GameMouseListener(mouseInputHandler))
        canvas.addMouseMotionListener(GameMouseListener(mouseInputHandler))

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

    fun updateScreen() {
        graphics.drawImage(screen.bufferedImage, START_X, START_Y, canvas.width, canvas.height, null)
        bufferStrategy.show()
    }

    fun updateInput() {
        keyInputHandler.update()
        mouseInputHandler.update()
    }

    companion object {
        const val NUMBER_OF_BUFFERS = 2
        const val START_X = 0
        const val START_Y = 0
    }
}