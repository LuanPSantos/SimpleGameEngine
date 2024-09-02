package core.render

import core.Window

class GameRenderer(
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

    fun add(renderable: Renderable): GameRenderer {
        renderableList.add(renderable)

        return this
    }
}