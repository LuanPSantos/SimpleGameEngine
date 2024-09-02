package core.input

import java.awt.event.MouseEvent
import java.awt.event.MouseMotionListener

class GameMouseMotionListener(
    private val inputHandler: GameMouseMotionEventHandler
) : MouseMotionListener {
    override fun mouseDragged(event: MouseEvent) {
        inputHandler.mouseDragged(event)
    }

    override fun mouseMoved(event: MouseEvent) {
        inputHandler.mouseMoved(event)
    }
}