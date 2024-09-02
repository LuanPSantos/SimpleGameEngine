package core.render

import core.Window
import core.math.Position

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
        }

        if (position.y < 0) {
            newY -= position.y
        }

        if (newWidth + position.x > screen.width) {
            newWidth -= newWidth + position.x - screen.width
        }

        if (newHeight + position.y > screen.height) {
            newHeight -= newHeight + position.y - screen.height
        }

        for (y in newY..<newHeight) {
            for (x in newX..<newWidth) {
                val pixelPosition = Position(
                    position.x + x,
                    position.y + y
                )
                setPixel(pixels[x + y * width], pixelPosition, screen)
            }
        }
    }

    private fun setPixel(pixelValue: Int, pixelPosition: Position, screen: Window.Screen) {
        if (pixelPosition.x < 0 || pixelPosition.x >= screen.width
            || pixelPosition.y < 0 || pixelPosition.y >= screen.height
            || pixelValue == 0xFFFF00FF.toInt()
        ) return

        screen.pixels[pixelPosition.x + pixelPosition.y * screen.width] = pixelValue
    }

}