package core

import core.render.Renderable

abstract class GameObject {

    val graphics = mutableListOf<Renderable>()

    abstract fun update()

}