package core.render

import core.GameObject
import core.Screen
import core.math.Transform
import core.math.Vector2
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.round

class Camera(
    private val screen: Screen
) {
    val transform = Transform()

    init {
        transform.scale = Vector2(0.9, 1.0)
    }

    fun capture(gameObject: GameObject) {
        println(transform.scale)
        gameObject.graphics.forEach { renderable ->

            var newX = 0
            var newY = 0
            var newWidth = round(renderable.width * abs(gameObject.transform.scale.x) ).toInt()
            var newHeight = round(renderable.height * abs(gameObject.transform.scale.y)).toInt()

            val deltaX = (gameObject.transform.position.x - transform.position.x).toInt()
            val deltaY = (gameObject.transform.position.y - transform.position.y).toInt()

            if (deltaX < -newWidth) return
            if (deltaY < -newHeight) return
            if (deltaX >= screen.width * transform.scale.x) return
            if (deltaY >= screen.height * transform.scale.y) return

            if (gameObject.transform.position.x < transform.position.x) {
                newX += abs(deltaX)
            }

            if (gameObject.transform.position.y < transform.position.y) {
                newY += abs(deltaY)
            }

            if (newWidth + gameObject.transform.position.x > transform.position.x + screen.width * transform.scale.x) {
                newWidth -= ((gameObject.transform.position.x + newWidth) - (transform.position.x)).toInt()
            }

            if (newHeight + gameObject.transform.position.y > transform.position.y + screen.height * transform.scale.y) {
                newHeight -= ((gameObject.transform.position.y + newHeight) - (transform.position.y)).toInt()
            }

            for (y in newY..<newHeight) {
                for (x in newX..<newWidth) {
                    val pixelPosition = Vector2(
                        deltaX + x,
                        deltaY + y
                    )

                    var scaledX = applyScale(x, renderable.width, gameObject.transform.scale.x)
                    var scaledY = applyScale(y, renderable.height, gameObject.transform.scale.y)

                    scaledX = applyScale(scaledX, renderable.width, 1/transform.scale.x)
                    scaledY = applyScale(scaledY, renderable.height, 1/transform.scale.y)

                    screen.setPixelAt(renderable.getPixelAt(scaledX, scaledY), pixelPosition)
                }
            }
        }
    }

    //TODO fazer isso certo
    private fun applyScale(index: Int, originalSize: Int, scaleFactor: Double): Int {
        var scaled = floor(index / abs(scaleFactor)).toInt()

        if(scaleFactor < 0) {
            scaled = (originalSize - scaled) - 1
        }

        return scaled
    }
}