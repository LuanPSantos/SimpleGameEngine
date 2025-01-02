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

    fun capture(gameObject: GameObject) {
        gameObject.graphics.forEach { renderable ->

            var newX = 0
            var newY = 0
            var newWidth = round(renderable.width * abs(gameObject.transform.scale.x) ).toInt()
            var newHeight = round(renderable.height * abs(gameObject.transform.scale.y)).toInt()

            val deltaX = (gameObject.transform.position.x - transform.position.x).toInt()
            val deltaY = (gameObject.transform.position.y - transform.position.y).toInt()

            if (deltaX < -newWidth) return
            if (deltaY < -newHeight) return
            if (deltaX >= screen.width) return
            if (deltaY >= screen.height) return

            if (gameObject.transform.position.x < transform.position.x) {
                newX += abs(deltaX)
            }

            if (gameObject.transform.position.y < transform.position.y) {
                newY += abs(deltaY)
            }

            if (newWidth + gameObject.transform.position.x > transform.position.x +screen.width) {
                newWidth -= ((gameObject.transform.position.x + newWidth) - (transform.position.x + screen.width)).toInt()
            }

            if (newHeight + gameObject.transform.position.y > screen.height) {
                newHeight -= ((gameObject.transform.position.y + newHeight) - (transform.position.y + screen.height)).toInt()
            }

            for (y in newY..<newHeight) {
                for (x in newX..<newWidth) {
                    val pixelPosition = Vector2(
                        deltaX + x,
                        deltaY + y
                    )

                    val scaledX = applyScale(x, renderable.width, gameObject.transform.scale.x)
                    val scaledY = applyScale(y, renderable.height, gameObject.transform.scale.y)

                    screen.setPixelAt(renderable.getPixelAt(scaledX, scaledY), pixelPosition)
                }
            }
        }
    }

    private fun applyScale(index: Int, originalSize: Int, scaleFactor: Float): Int {
        var scaled = floor(index / abs(scaleFactor)).toInt()

        if(scaleFactor < 0) {
            scaled = (originalSize - scaled) - 1
        }

        return scaled
    }
}