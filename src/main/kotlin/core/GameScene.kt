package core

import core.math.Vector2
import core.render.Camera

class GameScene private constructor(
    private val camera: Camera,
    private val screen: Screen
) {

    private val gameObjects = mutableListOf<GameObject>()
    private val origin = Vector2(0.0, 0.0)

    fun update() {
        gameObjects.forEach { it.update() }
    }

    fun render() {
        screen.clear()

        gameObjects.forEach { camera.capture(it) }
    }

    fun addGameObject(gameObject: GameObject): GameScene {
        gameObjects.add(gameObject)

        return this
    }

    companion object {
        private var scene: GameScene? = null

        fun new(camera: Camera, screen: Screen): GameScene {
            if (scene == null) {
                scene = GameScene(camera, screen)
            }

            return scene!!
        }

        fun instance(): GameScene {
            return scene!!
        }
    }
}