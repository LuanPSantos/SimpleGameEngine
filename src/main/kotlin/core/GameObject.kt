package core

import core.math.Transform
import core.render.Renderable

abstract class GameObject {

    val graphics = mutableListOf<Renderable>()
    val transform = Transform()

    abstract fun update()

}