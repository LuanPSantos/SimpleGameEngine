package core.input

import core.math.Vector2
import java.awt.event.MouseEvent
import kotlin.math.roundToInt

class GameMouseInput(
    private val scale: Double
) {
    private val currentButtonsPressed = BooleanArray(256)
    private val previousButtonsPressed = BooleanArray(256)

    var mousePosition: Vector2<Int> = Vector2(0, 0)

    fun update() {
        for (button in currentButtonsPressed.indices) {
            previousButtonsPressed[button] = currentButtonsPressed[button]
        }
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

    fun isHoldingButton(button: Int): Boolean {
        return currentButtonsPressed[button] && previousButtonsPressed[button]
    }

    fun isButtonPressed(button: Int): Boolean {
        return currentButtonsPressed[button] && !previousButtonsPressed[button]
    }

    fun isButtonReleased(button: Int): Boolean {
        return !currentButtonsPressed[button] && previousButtonsPressed[button]
    }
}