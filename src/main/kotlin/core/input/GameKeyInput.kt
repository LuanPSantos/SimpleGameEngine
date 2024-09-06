package core.input

import core.math.Vector2
import java.awt.event.KeyEvent

class GameKeyInput {
    private val currentKeysPressed = BooleanArray(256)
    private val previousKeysPressed = BooleanArray(256)
    private val keyEvents = Array<KeyEvent?>(256) { null }

    fun update() {
        for (keyCode in currentKeysPressed.indices) {
            previousKeysPressed[keyCode] = currentKeysPressed[keyCode]
        }
    }

    fun keyPressed(event: KeyEvent) {
        currentKeysPressed[event.keyCode] = true
        keyEvents[event.keyCode] = event

    }

    fun keyReleased(event: KeyEvent) {
        currentKeysPressed[event.keyCode] = false
        keyEvents[event.keyCode] = event
    }

    fun isHoldingKey(keyCode: Int): Boolean {
        return currentKeysPressed[keyCode] && previousKeysPressed[keyCode]
    }

    fun isKeyPressed(keyCode: Int): Boolean {
        return currentKeysPressed[keyCode] && !previousKeysPressed[keyCode]
    }

    fun isKeyReleased(keyCode: Int): Boolean {
        return !currentKeysPressed[keyCode] && previousKeysPressed[keyCode]
    }

    fun getDirection(): Vector2<Int> {
        val left = isHoldingKey(KeyEvent.VK_A)
                || isHoldingKey(KeyEvent.VK_LEFT)
                || isHoldingKey(KeyEvent.VK_KP_LEFT)

        val right = isHoldingKey(KeyEvent.VK_D)
                || isHoldingKey(KeyEvent.VK_RIGHT)
                || isHoldingKey(KeyEvent.VK_KP_RIGHT)

        val up = isHoldingKey(KeyEvent.VK_W)
                || isHoldingKey(KeyEvent.VK_UP)
                || isHoldingKey(KeyEvent.VK_KP_UP)

        val down = isHoldingKey(KeyEvent.VK_S)
                || isHoldingKey(KeyEvent.VK_DOWN)
                || isHoldingKey(KeyEvent.VK_KP_DOWN)

        val x = (if (left) -1 else 0) + (if (right) 1 else 0)
        val y = (if (up) -1 else 0) + (if (down) 1 else 0)

        return Vector2(x, y)
    }
}