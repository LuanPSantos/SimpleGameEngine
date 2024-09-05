package core.input

import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener

class GameMouseListener(
    private val inputHandler: GameMouseInput
) : MouseListener, MouseMotionListener {
    override fun mouseClicked(event: MouseEvent?) {

    }

    override fun mousePressed(event: MouseEvent) {
        inputHandler.mousePressed(event)
    }

    override fun mouseReleased(event: MouseEvent) {
        inputHandler.mouseReleased(event)
    }

    override fun mouseEntered(event: MouseEvent) {

    }

    override fun mouseExited(event: MouseEvent) {

    }

    override fun mouseDragged(event: MouseEvent) {
        inputHandler.mouseDragged(event)
    }

    override fun mouseMoved(event: MouseEvent) {
        inputHandler.mouseMoved(event)
    }

}