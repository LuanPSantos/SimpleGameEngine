package game

import core.GameLoop
import core.GameObject
import core.input.GameKeyInput
import core.math.Vector2
import core.render.Camera
import kotlin.math.cos
import kotlin.math.sin

class CameraController(
    private val camera: Camera,
    private val speed: Float,
    private val keyInput: GameKeyInput
) : GameObject() {


    override fun update() {

        camera.transform.position = Vector2(
            camera.transform.position.x + keyInput.getDirection().x * speed * GameLoop.DELTA_TIME,
            camera.transform.position.y + keyInput.getDirection().y * speed * GameLoop.DELTA_TIME
        )
    }
}