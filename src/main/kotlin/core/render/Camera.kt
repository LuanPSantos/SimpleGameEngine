package core.render

import core.GameObject
import core.Screen
import core.math.Transform
import core.math.Vector2
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.round

class Camera(
    val screen: Screen
) {
    val transform = Transform()

    fun capture(gameObject: GameObject) {
        gameObject.graphics.forEach {

            val deltaX = (gameObject.transform.position.x - transform.position.x).toInt()
            val deltaY = (gameObject.transform.position.y - transform.position.y).toInt()

            if (deltaX < -it.width) return
            if (deltaY < -it.height) return
            if (gameObject.transform.position.x >= screen.width) return
            if (gameObject.transform.position.y >= screen.height) return

            var newX = 0
            var newY = 0
            var newWidth = round(it.width * abs(gameObject.transform.scale.x) ).toInt()
            var newHeight = round(it.height * abs(gameObject.transform.scale.y)).toInt()

            if (gameObject.transform.position.x < transform.position.x) {
                newX -= transform.position.x.toInt()
            }

            if (gameObject.transform.position.y < transform.position.y) {
                newY -= transform.position.y.toInt()
            }

            if (newWidth + gameObject.transform.position.x > screen.width) {
                newWidth -= newWidth + deltaX - screen.width
            }

            if (newHeight + gameObject.transform.position.y > screen.height) {
                newHeight -= newHeight + deltaY - screen.height
            }

            //TODO Fix scale on corner
            for (y in newY..<newHeight) {
                for (x in newX..<newWidth) {
                    val pixelPosition = Vector2(
                        deltaX + x,
                        deltaY + y
                    )

                    val scaledX = applyScale(x, it.width, newWidth, gameObject.transform.scale.x)
                    val scaledY = applyScale(y, it.height, newHeight, gameObject.transform.scale.y)

                    screen.setPixelAt(it.getPixelAt(scaledX, scaledY), pixelPosition)
                }
            }
        }
    }

    private fun applyScale(index: Int, originalSize: Int, newSize: Int, scaleFactor: Float): Int {
        var scaled = floor(index * (originalSize.toFloat() / newSize.toFloat())).toInt()

        if(scaleFactor < 0) {
            scaled = originalSize - 1 - scaled
        }

        return scaled
    }
}