package core.input

import core.math.Position
import java.awt.event.MouseEvent
import kotlin.math.roundToInt

class GameMouseInput(
    private val scale: Double
) {
    private val currentButtonsPressed = BooleanArray(256)
    private val previousButtonsPressed = BooleanArray(256)

    var mousePosition: Position = Position()

    fun update() {
        for (button in currentButtonsPressed.indices) {
            previousButtonsPressed[button] = currentButtonsPressed[button]

//            if (isHoldingButton(button)) {
//                mousePosition = Position(keyEvents[button]?.x ?: 0, keyEvents[button]?.y ?: 0)
//            } else {
//                keyEvents[button] = null
//            }
        }
    }

    fun mousePressed(event: MouseEvent) {
        currentButtonsPressed[event.button] = true
    }

    fun mouseReleased(event: MouseEvent) {
        currentButtonsPressed[event.button] = false
    }

    fun mouseDragged(event: MouseEvent) {
        mousePosition = Position(
            (event.x / scale).roundToInt(),
            (event.y / scale).roundToInt()
        )
    }

    fun mouseMoved(event: MouseEvent) {
        mousePosition = Position(
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