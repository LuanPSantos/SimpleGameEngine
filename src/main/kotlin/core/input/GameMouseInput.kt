package core.input

import core.math.Vector2
import java.awt.event.MouseEvent
import java.awt.event.MouseWheelEvent
import kotlin.math.roundToInt

class GameMouseInput(
    private val scale: Double
) {
    private val currentButtonsPressed = BooleanArray(256)
    private val previousButtonsPressed = BooleanArray(256)

    private var mousePosition: Vector2<Int> = Vector2(0, 0)
    private var mouseWheelDirection: Int = 0

    fun update() {
        for (button in currentButtonsPressed.indices) {
            previousButtonsPressed[button] = currentButtonsPressed[button]
        }

        mouseWheelDirection = 0
    }

    fun mousePressed(event: MouseEvent) {
        currentButtonsPressed[event.button] = true
    }

    fun mouseReleased(event: MouseEvent) {
        currentButtonsPressed[event.button] = false
    }

    fun mouseDragged(event: MouseEvent) {
        mousePosition = Vector2(
            (event.x / scale).roundToInt(),
            (event.y / scale).roundToInt()
        )
    }

    fun mouseMoved(event: MouseEvent) {
        mousePosition = Vector2(
            (event.x / scale).roundToInt(),
            (event.y / scale).roundToInt()
        )
    }

    fun mouseWheelMoved(event: MouseWheelEvent) {
        mouseWheelDirection = event.wheelRotation

        println("mouseWheelMoved $mouseWheelDirection")
    }

    fun isHoldingButton(button: Int): Boolean {
        return currentButtonsPressed[button] && previousButtonsPressed[button]
    }

    fun isButtonPressed(button: Int): Boolean {
        return currentButtonsPressed[button] && !previousButtonsPressed[button]
    }

    fun isButtonReleased(button: Int): Boolean {
        return !currentButtonsPressed[button] && previousButtonsPressed[button]
    }

    fun mouseWheelDirection(): Int {
        return mouseWheelDirection
    }

    fun mousePosition(): Vector2<Int> {
        return mousePosition
    }
}