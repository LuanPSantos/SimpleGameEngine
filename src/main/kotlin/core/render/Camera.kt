package core.render

import core.GameObject
import core.Screen
import core.math.Vector2
import java.awt.Color
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.round

class Camera(
    private val screen: Screen
) {
    var zoom = 2f

    fun capture(gameObject: GameObject) {

        gameObject.graphics.forEach { renderable ->
            val zoomedWidth = round(screen.width / zoom).toInt()
            val zoomedHeight = round(screen.height / zoom).toInt()

            val newCameraBoundaryX = (screen.width - zoomedWidth) / 2
            val newCameraBoundaryY = (screen.height - zoomedHeight) / 2

            val objectRelativePositionX = (gameObject.transform.position.x - newCameraBoundaryX).toInt()
            val objectRelativePositionY = (gameObject.transform.position.y - newCameraBoundaryY).toInt()

            // Object out of view is ignored
            if (objectRelativePositionX < -gameObject.transform.scale.x) return
            if (objectRelativePositionY < -gameObject.transform.scale.y) return
            if (objectRelativePositionX >= zoomedWidth) return
            if (objectRelativePositionY >= zoomedHeight) return

            val position = Vector2(
                round(gameObject.transform.position.x).toInt(),
                round(gameObject.transform.position.y).toInt()
            )

            val scale = Vector2(
                round(gameObject.transform.scale.x * renderable.width).toInt(),
                round(gameObject.transform.scale.y * renderable.height).toInt()
            )


            for (y in 0..<scale.y) {
                for (x in 0..<scale.x) {

                    val pixelPosition = Vector2(
                        position.x + x,
                        position.y + y
                    )

                    val originalPixPosition = getRelativePixelPosition(Vector2(x, y), renderable, gameObject.transform.scale)

                    if(
                        pixelPosition.x < (newCameraBoundaryX + 2) ||
                        pixelPosition.y < (newCameraBoundaryY + 2) ||
                        pixelPosition.x >= (newCameraBoundaryX + zoomedWidth - 2) ||
                        pixelPosition.y >= newCameraBoundaryY + zoomedHeight - 2) {

                        screen.setPixelAt(Color.GREEN.rgb, pixelPosition)
                    }else{
                        screen.setPixelAt(renderable.getPixelAt(originalPixPosition.x, originalPixPosition.y), pixelPosition)
                    }
                }
            }
        }
    }

    private fun getRelativePixelPosition(pixelPosition: Vector2<Int>, renderable: Renderable, scaleFactor: Vector2<Double>): Vector2<Int> {
        var scaled = Vector2(
            floor(pixelPosition.x / abs(scaleFactor.x)).toInt(),
            floor(pixelPosition.y / abs(scaleFactor.y)).toInt()
        )

        if(scaleFactor.x < 0) {
            scaled = Vector2(
                (renderable.width - scaled.x) - 1,
                scaled.y
            )
        }

        if(scaleFactor.y < 0) {
            scaled = Vector2(
                scaled.x,
                (renderable.height - scaled.y) - 1
            )
        }

        return scaled
    }
}