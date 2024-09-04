package core.render

import core.Window
import core.math.Position
import game.Sprite

interface Renderable {

    fun render(screen: Window.Screen)

    companion object {
        fun setPixelOnScreen(pixelValue: Int, pixelPosition: Position, screen: Window.Screen) {
            if (pixelPosition.x < 0 || pixelPosition.x >= screen.width
                || pixelPosition.y < 0 || pixelPosition.y >= screen.height
                || pixelValue == Sprite.PINK
            ) return

            screen.setPixelAt(pixelPosition.x, pixelPosition.y, pixelValue)
        }
    }
}