package game

import core.GameLoop
import core.GameObject
import core.input.GameKeyInput
import core.input.GameMouseInput
import core.math.Vector2
import core.render.Camera
import java.awt.event.KeyEvent
import kotlin.math.cos
import kotlin.math.sin

class CameraController(
    private val camera: Camera,
    private val speed: Float,
    private val keyInput: GameKeyInput
) : GameObject() {


    override fun update() {

        val zoom = if (keyInput.isHoldingKey(KeyEvent.VK_C)) {
            1.0
        } else if (keyInput.isHoldingKey(KeyEvent.VK_V)) {
            -1.0
        } else {
            0.0
        }

        camera.transform.scale = camera.transform.scale.let { Vector2(
            it.x + zoom ,
            it.y + zoom
        ) }

        camera.transform.position = Vector2(
            camera.transform.position.x + keyInput.getDirection().x * speed * GameLoop.DELTA_TIME,
            camera.transform.position.y + keyInput.getDirection().y * speed * GameLoop.DELTA_TIME
        )
    }
}