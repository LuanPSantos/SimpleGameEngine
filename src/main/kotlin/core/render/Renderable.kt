package core.render

import core.Window
import core.math.Position
import kotlin.math.roundToInt

abstract class Renderable(
    private val pixels: IntArray,
    private val width: Int,
    private val height: Int,
    var position: Position
) {

    fun render(screen: Window.Screen) {
        if (position.x < -width) return
        if (position.y < -height) return
        if (position.x >= screen.width) return
        if (position.y >= screen.height) return

        var newX = 0
        var newY = 0
        var newWidth = width
        var newHeight = height

        if (position.x < 0) {
            newX -= position.x
            println(newX)
        }

        if (position.y < 0) {
            newY -= position.y
            println(newY)
        }

        if (newWidth + position.x > screen.width) {
            newWidth -= newWidth + position.x - screen.width
            println(newWidth)
        }

        if (newHeight + position.y > screen.height) {
            newHeight -= newHeight + position.y - screen.height
            println(newHeight)
        }

        for (y in newY..<newHeight) {
            for (x in newX..<newWidth) {
                val positionOnScreen =
                    Position(
                        (position.x / screen.scale.roundToInt()) + x,
                        (position.y / screen.scale.roundToInt()) + y
                    )
                setPixel(pixels[x + y * width], positionOnScreen, screen)
            }
        }
    }

    private fun setPixel(pixelValue: Int, positionOnScreen: Position, screen: Window.Screen) {
        if (positionOnScreen.x < 0 || positionOnScreen.x >= screen.width
            || positionOnScreen.y < 0 || positionOnScreen.y >= screen.height
            || pixelValue == 0xFFFF00FF.toInt()
        ) return

        screen.pixels[positionOnScreen.x + positionOnScreen.y * screen.width] = pixelValue
    }

}