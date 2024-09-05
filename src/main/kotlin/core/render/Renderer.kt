package core.render

import core.GameObject
import core.Window

class Renderer(
    private val window: Window
) {

    private val renderableList = mutableListOf<Renderable>()

    fun render() {
        window.update {
            for (renderable in renderableList) {
                renderable.render(it)
            }
        }
    }

    fun add(gameObject: GameObject): Renderer {

        renderableList.addAll(gameObject.graphics)

        return this
    }
}