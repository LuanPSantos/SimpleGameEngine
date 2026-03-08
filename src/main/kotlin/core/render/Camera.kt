package core.render

import core.GameObject
import core.Screen
import core.math.Transform
import core.math.Vector2
import java.awt.Color
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.round

class Camera(
    private val screen: Screen
) {
    val transform = Transform()

    init {
        transform.scale = Vector2(0.1, 0.1)
    }

    fun capture(gameObject: GameObject) {

        gameObject.graphics.forEach { renderable ->
            // o zoom ta funcionando porem precisa ser invertido.
            var newX = 0
            var newY = 0
            var newWidth = round(renderable.width * abs(gameObject.transform.scale.x) * transform.scale.x).toInt()
            var newHeight = round(renderable.height * abs(gameObject.transform.scale.y) * transform.scale.y).toInt()

            val deltaX = (gameObject.transform.position.x - transform.position.x).toInt()
            val deltaY = (gameObject.transform.position.y - transform.position.y).toInt()

            if (deltaX < -newWidth) return
            if (deltaY < -newHeight) return
            if (deltaX >= screen.width * transform.scale.x) return
            if (deltaY >= screen.height * transform.scale.y) return

            if (gameObject.transform.position.x < transform.position.x) {
                newX = round(abs(deltaX) * transform.scale.x).toInt()
            }

            if (gameObject.transform.position.y < transform.position.y) {
                newY = round(abs(deltaY) * transform.scale.y).toInt()
            }

            if (newWidth + gameObject.transform.position.x > transform.position.x + screen.width * transform.scale.x) {
                newWidth -= ((gameObject.transform.position.x + newWidth) - (transform.position.x + screen.width * transform.scale.x)).toInt()
            }

            if (newHeight + gameObject.transform.position.y > transform.position.y + screen.height * transform.scale.y) {
                newHeight -= ((gameObject.transform.position.y + newHeight) - (transform.position.y + screen.height * transform.scale.y)).toInt()
            }

            for (y in newY..<newHeight) {
                for (x in newX..<newWidth) {


                    var scaledX = applyScale(x, renderable.width, gameObject.transform.scale.x)
                    var scaledY = applyScale(y, renderable.height, gameObject.transform.scale.y)
//
//                    scaledX = applyScale(deltaX + x, screen.width, transform.scale.x)
//                    scaledY = applyScale(deltaY + y, screen.height, transform.scale.y)

                    val pixelPosition = Vector2(
                        deltaX + x,
                        deltaY + y
                    )

                    if(pixelPosition.x <= 2 || pixelPosition.x +2 >= screen.width * transform.scale.x ||
                        pixelPosition.y <= 2 || pixelPosition.y +2>= screen.height * transform.scale.y) {
                        screen.setPixelAt(Color.GREEN.rgb, pixelPosition)
                    }else{
                        screen.setPixelAt(renderable.getPixelAt(scaledX, scaledY), pixelPosition)
                    }

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