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

    var angle = 0f

    override fun update() {
        angle = (angle+(1))
        camera.transform.position = Vector2(
            (speed * GameLoop.DELTA_TIME + camera.transform.position.x * cos(angle.toDouble())).toFloat(),
            0f
        )
    }
}