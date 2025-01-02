package game

import core.GameLoop
import core.GameObject
import core.input.GameKeyInput
import core.input.GameMouseInput
import core.math.Vector2
import core.render.Image
import core.render.TileAnimation
import kotlin.math.abs
import kotlin.math.roundToInt

class Player(
    private val mouseInput: GameMouseInput,
    private val keyInput: GameKeyInput,
    private val speed: Float,
    sprite: Image
) : GameObject() {

    init {
        graphics.add(sprite)

        transform.position = Vector2(100f, 100f)
    }

    override fun update() {

//
//        position = Vector2(
//            (speed * mouseInput.mousePosition.x * DELTA_TIME + position.x),
//            (speed * mouseInput.mousePosition.y * DELTA_TIME + position.y)
//        )


        transform.position = transform.position.let { Vector2(
            it.x + (keyInput.getDirection().x * speed * GameLoop.DELTA_TIME).toFloat(),
            it.y + (keyInput.getDirection().y * speed * GameLoop.DELTA_TIME).toFloat()) }



//        if(keyInput.getDirection().x < 0 && transform.scale.x > 0) {
//            transform.scale = transform.scale.let { Vector2(-it.x, it.y) }
//        }
//        if(keyInput.getDirection().x > 0 && transform.scale.x < 0) {
//            transform.scale = transform.scale.let { Vector2(abs(it.x), it.y) }
//        }
//        if(keyInput.getDirection().y < 0 && transform.scale.y > 0) {
//            transform.scale = transform.scale.let { Vector2(it.x, -it.y) }
//        }
//        if(keyInput.getDirection().y > 0 && transform.scale.y < 0) {
//            transform.scale = transform.scale.let { Vector2(it.x, abs(it.y)) }
//        }
    }
}