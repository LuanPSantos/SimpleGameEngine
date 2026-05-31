package core.render

import core.GameObject
import core.Screen
import core.math.Transform
import core.math.Vector2
import java.awt.Color
import javax.swing.Spring.scale
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.round

class Camera(
    private val screen: Screen
) {

    private var zoom = 1.0
    val transform:Transform = Transform()

    init {
        transform.scale = Vector2(screen.width / zoom, screen.height / zoom)
        transform.position = Vector2(transform.position.x + ((0 - transform.scale.x)/2), transform.position.y + ((0 - transform.scale.y)/2))
    }

    fun updateZoom(zoomDirection: Int) {
        if(zoomDirection > 0) {
            zoom *= 0.9
        } else if(zoomDirection < 0) {
            zoom *= 1.1
        }
        val previous = transform.scale.copy()
        transform.scale = Vector2(screen.width / zoom, screen.height / zoom)

        transform.position = Vector2(transform.position.x + ((previous.x - transform.scale.x)/2), transform.position.y + ((previous.y - transform.scale.y)/2))
    }

    fun capture(gameObject: GameObject) {

        val ratio = Vector2(
            (transform.scale.x) / screen.width,
            (transform.scale.y) / screen.height
        )

        gameObject.graphics.forEach { renderable ->

            // Object out of view is ignored
            if (transform.position.x > (gameObject.transform.position.x + gameObject.transform.scale.x)) return
            if (transform.position.y > -(gameObject.transform.position.y + gameObject.transform.scale.y)) return
            if (transform.position.x + transform.scale.x < gameObject.transform.position.x) return
            if (transform.position.y + transform.scale.y < -gameObject.transform.position.y) return

            val objectIntScaled = Vector2(
                floor(gameObject.transform.scale.x * renderable.width * zoom).toInt(),
                floor(gameObject.transform.scale.y * renderable.height * zoom).toInt()
            )

            for (y in 0..<objectIntScaled.y) {
                for (x in 0..<objectIntScaled.x) {

                    val pixelScreenPosition = Vector2(
                        round(((gameObject.transform.position.x + x) - transform.position.x) / ratio.x).toInt(),
                        round((-(gameObject.transform.position.y + y) - transform.position.y) / ratio.y).toInt()
                    )

                    val originalPixPosition =
                        getRelativePixelPosition(Vector2(x, y), renderable, gameObject.transform.scale, ratio)
                    screen.setPixelAt(
                        renderable.getPixelAt(originalPixPosition.x, originalPixPosition.y),
                        pixelScreenPosition
                    )
                }
            }
        }
    }

    private fun getRelativePixelPosition(
        pixelPosition: Vector2<Int>,
        renderable: Renderable,
        scaleFactor: Vector2<Double>,
        ratio: Vector2<Double>
    ): Vector2<Int> {
        var scaled = Vector2(
            floor(pixelPosition.x / abs(scaleFactor.x ) * ratio.x).toInt(),
            floor(pixelPosition.y / abs(scaleFactor.y ) * ratio.y).toInt()
        )

        if (scaleFactor.x < 0) {
            scaled = Vector2(
                (renderable.width - scaled.x) - 1,
                scaled.y
            )
        }

        if (scaleFactor.y < 0) {
            scaled = Vector2(
                scaled.x,
                (renderable.height - scaled.y) - 1
            )
        }

        return scaled
    }
}