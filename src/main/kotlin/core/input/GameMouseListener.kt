package core.input

import java.awt.event.*

class GameMouseListener(
    private val inputHandler: GameMouseInput
) : MouseListener, MouseMotionListener, MouseWheelListener {
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

    override fun mouseWheelMoved(event: MouseWheelEvent) {
        inputHandler.mouseWheelMoved(event)
    }

}