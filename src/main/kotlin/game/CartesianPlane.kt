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
        graphics.add(grid)
    }
    override fun update() {
        grid.width = round(camera.transform.scale.x).toInt()
        grid.height = round(camera.transform.scale.y).toInt()
        transform.position = camera.transform.position

    }

    class Grid(private val camera: Camera): Renderable(0, 0) {
        override fun getPixelAt(x: Int, y: Int): Int {
            return if((round(camera.transform.position.x).toInt() + x) == 0 || round(camera.transform.position.y + y).toInt() == 0) {
                Color.DARK_GRAY.rgb
            }else{
                Color.MAGENTA.rgb
            }
        }
    }
}