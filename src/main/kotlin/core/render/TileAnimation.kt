package core.render

import core.GameLoop
import core.Screen

class TileAnimation(
    private val frames: Array<Image>,
    duration: Double = 1.0,
) : Renderable(frames.first().width, frames.first().height), Cloneable {

    private var timeCounter = 0.0
    private val frameDuration = duration / frames.size
    private var currentFrame = 0

    override fun getPixelAt(x: Int, y: Int): Int {
        //TODO fix animation
//        timeCounter += GameLoop.DELTA_TIME
//        if (timeCounter >= frameDuration) {
//            timeCounter = 0.0
//            currentFrame = (currentFrame + 1) % frames.size
//        }
        return frames[currentFrame].getPixelAt(x, y)
    }

    public override fun clone(): TileAnimation {
        return TileAnimation(frames, frameDuration * frames.size)
    }

    companion object {
        fun fromImage(tileMap: Image, tileWidth: Int, tileHeight: Int, duration: Double): TileAnimation {
            val horizontalTileCount = (tileMap.width / tileWidth)
            val verticalTileCount = (tileMap.height / tileHeight)
            val frames = Array(horizontalTileCount * verticalTileCount) {
                Image(tileWidth, tileHeight)
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