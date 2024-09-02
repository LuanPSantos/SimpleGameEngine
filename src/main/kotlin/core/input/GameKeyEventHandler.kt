package core.input

import java.awt.event.KeyEvent

class GameKeyEventHandler {
    private val currentKeysPressed = BooleanArray(256)
    private val previousKeysPressed = BooleanArray(256)
    private val keyEvents = Array<KeyEvent?>(256) { null }

    private val keyHoldingListeners = Array<MutableList<(event: KeyEvent) -> Unit>>(256) { mutableListOf() }
    private val keyPressedListeners = Array<MutableList<(event: KeyEvent) -> Unit>>(256) { mutableListOf() }
    private val keyReleasedListeners = Array<MutableList<(event: KeyEvent) -> Unit>>(256) { mutableListOf() }

    fun update() {
        for (keyCode in currentKeysPressed.indices) {
            previousKeysPressed[keyCode] = currentKeysPressed[keyCode]

            if (isHoldingKey(keyCode)) {
                keyHoldingListeners[keyCode].forEach { it(keyEvents[keyCode]!!) }
            } else {
                keyEvents[keyCode] = null
            }
        }
    }

    fun onHoldingKey(keyCode: Int, runnable: (event: KeyEvent) -> Unit) {
        keyHoldingListeners[keyCode].add(runnable)
    }

    fun onKeyPressed(keyCode: Int, runnable: (event: KeyEvent) -> Unit) {
        keyPressedListeners[keyCode].add(runnable)
    }

    fun onKeyReleased(keyCode: Int, runnable: (event: KeyEvent) -> Unit) {
        keyReleasedListeners[keyCode].add(runnable)
    }

    fun keyPressed(event: KeyEvent) {
        currentKeysPressed[event.keyCode] = true
        keyEvents[event.keyCode] = event

        if (!previousKeysPressed[event.keyCode]) {
            keyPressedListeners[event.keyCode].forEach { it(event) }
        }
    }

    fun keyReleased(event: KeyEvent) {
        currentKeysPressed[event.keyCode] = false
        keyEvents[event.keyCode] = event

        if (previousKeysPressed[event.keyCode]) {
            keyReleasedListeners[event.keyCode].forEach { it(event) }
        }
    }

    private fun isHoldingKey(keyCode: Int): Boolean {
        return currentKeysPressed[keyCode] && previousKeysPressed[keyCode]
    }
}