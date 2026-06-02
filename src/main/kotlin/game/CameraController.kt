package game

import core.GameObject
import core.input.GameMouseInput
import core.math.Vector2
import core.render.Camera
import java.awt.event.MouseEvent
import kotlin.math.round

class CameraController(
    private val camera: Camera,
    gameMouseInput: GameMouseInput
) : GameObject() , GameMouseInput.GameMouseInputListener {

    private var origin: Vector2<Int> = Vector2(0, 0)
    private var mousePosition: Vector2<Int> = Vector2(0, 0)

    init {
        gameMouseInput.gameMouseInputListeners.add(this)
    }

    override fun update() {

    }

    override fun mousePressed(event: MouseEvent, mousePosition: Vector2<Int>) {
        when (event.button) {
            MouseEvent.BUTTON1 -> {
                origin = mousePosition
                this.mousePosition = mousePosition
            }
        }
    }

    override fun mouseReleased(event: MouseEvent, mousePosition: Vector2<Int>) {

    }

    override fun mouseDragged(event: MouseEvent, mousePosition: Vector2<Int>) {
        val movement = Vector2(-(mousePosition.x - origin.x), -(mousePosition.y - origin.y))
        camera.position = Vector2(camera.position.x + movement.x.toDouble(), camera.position.y + movement.y.toDouble())
        origin  = mousePosition
    }

    override fun mouseWheelMoved(event: MouseEvent, mouseWheelDirection: Int) {
        camera.updateZoom(mouseWheelDirection)
    }
}