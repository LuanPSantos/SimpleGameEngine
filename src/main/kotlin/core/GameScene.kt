package core

class GameScene(
    private val screen: Screen
) {

    private val gameObjects = mutableListOf<GameObject>()

    fun update() {
        gameObjects.forEach { it.update() }
    }

    fun render() {
        screen.clear()

        gameObjects.forEach { it.render(screen) }
    }

    fun addGameObject(gameObject: GameObject): GameScene {
        gameObjects.add(gameObject)

        return this
    }
}