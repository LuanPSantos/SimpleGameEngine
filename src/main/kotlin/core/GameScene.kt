package core

import core.render.Renderer

class GameScene(
    private val renderer: Renderer
) {

    private val gameObjects = mutableListOf<GameObject>()

    fun update() {
        gameObjects.forEach { it.update() }
    }

    fun render() {
        renderer.render()
    }

    fun addGameObject(gameObject: GameObject): GameScene {
        gameObjects.add(gameObject)

        renderer.add(gameObject)

        return this
    }
}