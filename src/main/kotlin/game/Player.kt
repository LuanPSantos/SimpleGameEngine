package game

import core.GameLoop.Companion.DELTA_TIME
import core.GameObject
import core.input.GameKeyInput
import core.input.GameMouseInput
import core.math.Vector2
import core.render.Sprite
import core.render.TileAnimation
import kotlin.math.abs
import kotlin.math.roundToInt

class Player(
    private val mouseInput: GameMouseInput,
    private val keyInput: GameKeyInput,
    private val speed: Float
) : GameObject() {
    private val sprite: TileAnimation = TileAnimation.fromImage(Sprite.loadImage("/sprites/tileMap.png"), 32, 32, 1.0)
    private var position: Vector2<Double> = Vector2(0.0, 0.0)

    init {
        graphics.add(sprite)

        sprite.position = position.let { Vector2(it.x.roundToInt(), it.y.roundToInt()) }
    }

    override fun update() {

        position = Vector2(
            (speed * keyInput.getDirection().x * DELTA_TIME + position.x),
            (speed * keyInput.getDirection().y * DELTA_TIME + position.y)
        )

        sprite.position = position.let { Vector2(it.x.roundToInt(), it.y.roundToInt()) }

        if(keyInput.getDirection().x < 0 && sprite.scale.x > 0) {
            sprite.scale = sprite.scale.let { Vector2(-it.x, it.y) }
        }
        if(keyInput.getDirection().x > 0 && sprite.scale.x < 0) {
            sprite.scale = sprite.scale.let { Vector2(abs(it.x), it.y) }
        }
        if(keyInput.getDirection().y < 0 && sprite.scale.y > 0) {
            sprite.scale = sprite.scale.let { Vector2(it.x, -it.y) }
        }
        if(keyInput.getDirection().y > 0 && sprite.scale.y < 0) {
            sprite.scale = sprite.scale.let { Vector2(it.x, abs(it.y)) }
        }
    }
}