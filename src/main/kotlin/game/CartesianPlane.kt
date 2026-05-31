package game

import core.GameObject
import core.render.Renderable
import java.awt.Color

class CartesianPlane : GameObject() {
    init {
        graphics.add(Plane())
    }
    override fun update() {

    }

    class Plane: Renderable(100, 100) {
        override fun getPixelAt(x: Int, y: Int): Int {
            return if(x == 0 || y == 0) {
                Color.DARK_GRAY.rgb
            }else{
                Color.MAGENTA.rgb
            }
        }
    }
}