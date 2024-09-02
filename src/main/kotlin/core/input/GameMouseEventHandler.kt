package core.input

import java.awt.event.MouseEvent

class GameMouseEventHandler {
    private val currentButtonsPressed = BooleanArray(256)
    private val previousButtonsPressed = BooleanArray(256)
    private val keyEvents = Array<MouseEvent?>(256) { null }

    private val mouseHoldingListeners = Array<MutableList<(event: MouseEvent) -> Unit>>(15) { mutableListOf() }
    private val mousePressedListeners = Array<MutableList<(event: MouseEvent) -> Unit>>(15) { mutableListOf() }
    private val mouseReleasedListeners = Array<MutableList<(event: MouseEvent) -> Unit>>(15) { mutableListOf() }

    fun update() {
        for (button in currentButtonsPressed.indices) {
            previousButtonsPressed[button] = currentButtonsPressed[button]

            if (isHoldingButton(button)) {
                mouseHoldingListeners[button].forEach { it(keyEvents[button]!!) }
            } else {
                keyEvents[button] = null
            }
        }
    }

    fun onHoldingMouse(button: Int, runnable: (event: MouseEvent) -> Unit) {
        mouseHoldingListeners[button].add(runnable)
    }

    fun onMousePressed(button: Int, runnable: (event: MouseEvent) -> Unit) {
        mousePressedListeners[button].add(runnable)
    }

    fun onMouseReleased(button: Int, runnable: (event: MouseEvent) -> Unit) {
        mouseReleasedListeners[button].add(runnable)
    }

    fun mousePressed(event: MouseEvent) {
        currentButtonsPressed[event.button] = true
        keyEvents[event.button] = event

        if (!previousButtonsPressed[event.button]) {
            mousePressedListeners[event.button].forEach { it(event) }
        }
    }

    fun mouseReleased(event: MouseEvent) {
        currentButtonsPressed[event.button] = false
        keyEvents[event.button] = event

        if (previousButtonsPressed[event.button]) {
            mouseReleasedListeners[event.button].forEach { it(event) }
        }
    }

    private fun isHoldingButton(button: Int): Boolean {
        return currentButtonsPressed[button] && previousButtonsPressed[button]
    }
}