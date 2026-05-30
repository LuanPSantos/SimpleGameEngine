package game

import core.GameLoop
import core.GameObject
import core.input.GameKeyInput
import core.input.GameMouseInput
import core.math.Vector2
import core.render.Camera
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import kotlin.math.cos
import kotlin.math.sin

class CameraController(
    private val camera: Camera,
    private val speed: Float,
    private val gameMouseInput: GameMouseInput
) : GameObject() {

    override fun update() {
        //if(gameMouseInput.isHoldingButton(MouseEvent.BUTTON1)) {
        //    println("Mouse Button 1")
        //    camera.transform.position =
        //        Vector2(gameMouseInput.mousePosition().x.toDouble(), gameMouseInput.mousePosition().y.toDouble())
        //}

    }
}