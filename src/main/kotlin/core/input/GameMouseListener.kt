package core.input

import java.awt.event.MouseEvent
import java.awt.event.MouseListener

class GameMouseListener(
    private val inputHandler: GameMouseEventHandler
) : MouseListener {
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

}