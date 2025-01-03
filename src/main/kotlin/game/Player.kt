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
    sprite: Image
) : GameObject() {

    init {
        graphics.add(sprite)

        transform.position = Vector2(Random.nextDouble(0.0, 480.0), Random.nextDouble(0.0, 360.0))
        transform.scale = Vector2(Random.nextDouble(0.5, 3.0), Random.nextDouble(0.5, 3.0))
    }

    private var angleCounter = Random.nextDouble(360.0)

    override fun update() {
        angleCounter += GameLoop.DELTA_TIME * speed

        if (angleCounter >= 360) {
            angleCounter = 0.0
        }
        transform.position = transform.position.let {
            Vector2(
                it.x + sin(angleCounter),
                it.y + cos(angleCounter)
            )
        }
    }
}