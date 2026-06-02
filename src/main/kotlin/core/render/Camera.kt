package core.render

import core.GameObject
import core.Screen
import core.math.Transform
import core.math.Vector2
import java.awt.Color
import javax.swing.Spring.scale
import kotlin.contracts.contract
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round

class Camera(
    private val screen: Screen
) {

    private var zoom = 1.0
    var position: Vector2<Double> = Vector2(-50.0, -50.0)

    fun updateZoom(zoomDirection: Int) {
        if (zoomDirection > 0) {
            zoom *= 0.9
        } else if (zoomDirection < 0) {
            zoom *= 1.1
        }
    }

    fun capture(gameObject: GameObject) {


        gameObject.graphics.forEach { renderable ->

            //texel world position
            val leftBottom = Vector2(
                gameObject.transform.position.x,
                gameObject.transform.position.y
            )

            //texel world position
            val rightTop = Vector2(
                leftBottom.x + gameObject.transform.scale.x,
                leftBottom.y + gameObject.transform.scale.y
            )

            //texel screen position from texel world position
            val sx1 = (leftBottom.x - position.x) * zoom
            val sy1 = (-leftBottom.y - position.y) * zoom
            val sx2 = (rightTop.x - position.x) * zoom
            val sy2 = (-rightTop.y - position.y) * zoom

            val AA = Vector2(
                floor(min(sx1, sx2)).toInt(),
                floor(min(sy1, sy2)).toInt()
            )

            val BB = Vector2(
                floor(max(sx1, sx2)).toInt(),
                floor(max(sy1, sy2)).toInt()
            )

            // scan only the AABB pixels
            for (sy in AA.y..<(BB.y)) {
                for (sx in AA.x..<(BB.x)) {

                    // screen to world
                    val pixelWorldPosition = Vector2(
                        position.x + (sx / zoom),
                        -(position.y + (sy / zoom))
                    )

                    //world to texel
                    val texelPosition = Vector2(
                        floor((pixelWorldPosition.x - gameObject.transform.position.x) / gameObject.transform.scale.x * renderable.width).toInt(),
                        floor(((gameObject.transform.position.y + gameObject.transform.scale.y) - pixelWorldPosition.y) / gameObject.transform.scale.y * renderable.height).toInt()
                    )

                    //filter out of bound
                    if (texelPosition.x < 0 || texelPosition.y < 0 || texelPosition.x >= renderable.width || texelPosition.y >= renderable.height) continue

                    val pixel = renderable.getPixelAt(texelPosition.x, texelPosition.y)

                    screen.setPixelAt(
                        pixel,
                        Vector2(sx, sy)
                    )
                }
            }
        }
    }
}