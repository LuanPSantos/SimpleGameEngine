package game

import core.GameLoop
import core.Window
import core.math.Position
import core.render.Renderable

class TileAnimation(
    private val frames: Array<Sprite>,
    duration: Double = 1.0,
    var position: Position = Position()
) : Renderable {

    private var timeCounter = 0.0
    private val frameDuration = duration / frames.size
    private var currentFrame = 0

    override fun render(screen: Window.Screen) {
        frames[currentFrame].position = position
        frames[currentFrame].render(screen)

        timeCounter += GameLoop.DELTA_TIME
        if (timeCounter >= frameDuration) {
            timeCounter = 0.0
            currentFrame = (currentFrame + 1) % frames.size
        }
    }

    companion object {
        fun fromImage(tileMap: Sprite, tileWidth: Int, tileHeight: Int, duration: Double): TileAnimation {
            val horizontalTileCount = (tileMap.width / tileWidth)
            val verticalTileCount = (tileMap.height / tileHeight)
            val frames = Array(horizontalTileCount * verticalTileCount) {
                Sprite(tileWidth, tileHeight)
            }

            for (vertical in 0..<verticalTileCount) {
                for (horizontal in 0..<horizontalTileCount) {
                    val sprite = frames[horizontal + vertical * horizontalTileCount]
                    for (y in 0..<sprite.height) {
                        for (x in 0..<sprite.width) {
                            val offSetY = y + vertical * tileHeight
                            val offSetX = x + horizontal * tileWidth
                            if (offSetY < tileMap.height && offSetX < tileMap.width) {
                                sprite.pixels[y][x] = tileMap.pixels[offSetY][offSetX]
                            }
                        }
                    }
                }
            }

            return TileAnimation(frames, duration)
        }
    }
}