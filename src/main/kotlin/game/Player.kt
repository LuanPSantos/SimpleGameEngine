package game

import core.GameObject
import core.input.GameMouseInput
import core.render.Sprite
import java.awt.event.MouseEvent

class Player(
    private val mouseInput: GameMouseInput
) : GameObject() {
    private val sprite: Sprite = Sprite.loadImage("/sprites/yellow.png")

    init {
        graphics.add(sprite)
    }

    override fun update() {
        if (mouseInput.isHoldingButton(MouseEvent.BUTTON1)) {
            sprite.position = mouseInput.mousePosition
        }
    }
}