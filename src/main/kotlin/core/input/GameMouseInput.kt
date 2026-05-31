package core.input

import core.math.Vector2
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent
import kotlin.math.roundToInt

open class GameMouseInput(
    private val scale: Double
) {
    protected var mousePosition: Vector2<Int> = Vector2(0, 0)
    val gameMouseInputListeners: MutableList<GameMouseInputListener> = mutableListOf()

    fun mousePressed(event: MouseEvent) {
        gameMouseInputListeners.forEach { it.mousePressed(event, mousePosition) }
    }

    fun mouseReleased(event: MouseEvent) {
        gameMouseInputListeners.forEach { it.mouseReleased(event, mousePosition) }
    }

    fun mouseDragged(event: MouseEvent) {
        mousePosition = Vector2(
            (event.x / scale).roundToInt(),
            (event.y / scale).roundToInt()
        )

        gameMouseInputListeners.forEach { it.mouseDragged(event, mousePosition) }
    }

    fun mouseMoved(event: MouseEvent) {
        mousePosition = Vector2(
            (event.x / scale).roundToInt(),
            (event.y / scale).roundToInt()
        )
    }

    fun mouseWheelMoved(event: MouseWheelEvent) {
        gameMouseInputListeners.forEach { it.mouseWheelMoved(event, event.wheelRotation) }
    }

    interface GameMouseInputListener {
        fun mousePressed(event: MouseEvent, mousePosition: Vector2<Int>)
        fun mouseReleased(event: MouseEvent, mousePosition: Vector2<Int>)
        fun mouseDragged(event: MouseEvent, mousePosition: Vector2<Int>)
        fun mouseWheelMoved(event: MouseEvent, mouseWheelDirection: Int)
    }
}