package core

import core.render.Camera

class GameScene(
    private val camera: Camera,
    private val screen: Screen
) {

    private val gameObjects = mutableListOf<GameObject>()

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
}