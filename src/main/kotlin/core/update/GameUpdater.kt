package core.update

class GameUpdater {

    private val updatableObjects = mutableListOf<Updatable>()

    fun update() {
        updatableObjects.forEach { it.update() }
    }

    fun add(updatable: Updatable) {
        this.updatableObjects.add(updatable)
    }
}