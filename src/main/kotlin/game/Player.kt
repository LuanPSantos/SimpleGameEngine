package game

import core.GameLoop
import core.GameObject
import core.input.GameKeyInput
import core.input.GameMouseInput
import core.math.Vector2
import core.render.Image
import core.render.TileAnimation
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.random.Random

class Player(
    private val speed: Double,
    sprite: Image,
) : GameObject() {

    val orb: Vector2<Double>

    init {
        graphics.add(sprite)

        orb = Vector2(Random.nextDouble(-1480.0, 1480.0), Random.nextDouble(-1360.0, 1360.0))
        transform.scale = Vector2(20.0, 20.0)
    }

    private var angleCounter = Random.nextDouble(360.0)

    override fun update() {
        angleCounter += GameLoop.DELTA_TIME * speed

        if (angleCounter >= 360) {
            angleCounter = 0.0
        }
        transform.position =  Vector2(
            orb.x + 10 * cos(angleCounter),
            orb.y + 10 * sin(angleCounter)
        )
    }
}