package core.update

class GameUpdater {

    private val updatableObjects = mutableListOf<Updatable>()

    fun update(deltaTime: Double) {
        updatableObjects.forEach { it.update(deltaTime) }
    }

    fun add(updatable: Updatable) {
        this.updatableObjects.add(updatable)
    }
}