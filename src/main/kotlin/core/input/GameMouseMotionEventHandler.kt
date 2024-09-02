package core.input

import java.awt.Component
import java.awt.event.MouseEvent
import kotlin.math.roundToInt

class GameMouseMotionEventHandler(
    private val scale: Double
) {

    private val mouseMoveListeners = mutableListOf<(event: MouseEvent) -> Unit>()
    private val mouseDraggedListeners = mutableListOf<(event: MouseEvent) -> Unit>()

    fun onMouseMove(runnable: (event: MouseEvent) -> Unit) {
        mouseMoveListeners.add(runnable)
    }

    fun onMouseDragged(runnable: (event: MouseEvent) -> Unit) {
        mouseDraggedListeners.add(runnable)
    }

    fun mouseDragged(event: MouseEvent) {
        val newEvent = MouseEvent(
            event.source as Component,
            event.id,
            event.`when`,
            event.modifiers,
            (event.x / scale).roundToInt(),
            (event.y / scale).roundToInt(),
            event.xOnScreen,
            event.yOnScreen,
            event.clickCount,
            event.isPopupTrigger,
            event.button,
        )
        mouseDraggedListeners.forEach { it(newEvent) }
    }

    fun mouseMoved(event: MouseEvent) {
        val newEvent = MouseEvent(
            event.source as Component,
            event.id,
            event.`when`,
            event.modifiers,
            (event.x / scale).toInt(),
            (event.y / scale).toInt(),
            event.xOnScreen,
            event.yOnScreen,
            event.clickCount,
            event.isPopupTrigger,
            event.button,
        )
        mouseMoveListeners.forEach { it(newEvent) }
    }
}