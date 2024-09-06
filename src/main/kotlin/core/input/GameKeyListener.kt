package core.input

import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class GameKeyListener(
    private val inputHandler: GameKeyInput
) : KeyListener {
    override fun keyTyped(event: KeyEvent) {

    }

    override fun keyPressed(event: KeyEvent) {
        inputHandler.keyPressed(event)
    }

    override fun keyReleased(event: KeyEvent) {
        inputHandler.keyReleased(event)
    }
}