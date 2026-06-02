package game

import core.GameObject
import core.math.Vector2
import core.render.Camera
import core.render.Renderable
import java.awt.Color
import kotlin.math.round

class CartesianPlane(
    private val camera: Camera
) : GameObject() {
    private val grid = Grid(camera)
    init {
        //graphics.add(grid)
    }
    override fun update() {


    }

    class Grid(private val camera: Camera): Renderable(0, 0) {
        override fun getPixelAt(x: Int, y: Int): Int {
            return Color.DARK_GRAY.rgb
        }
    }
}