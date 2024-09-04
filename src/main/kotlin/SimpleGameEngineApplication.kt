import core.*
import core.Window.Screen
import core.input.*
import core.math.Position
import core.render.GameRenderer
import core.update.GameUpdater
import game.Sprite
import game.TileAnimation
import java.awt.event.MouseEvent


fun main() {
    val window = Window(960, 720, 2.0)
    val keyInputHandler = GameKeyEventHandler()
    val mouseInputHandler = GameMouseEventHandler()
    val mouseMotionInputHandler = GameMouseMotionEventHandler(2.0)

    window.addCanvasKeyListener(GameKeyListener(keyInputHandler))
    window.addCanvasMouseListener(GameMouseListener(mouseInputHandler))
    window.addCanvasMouseMotionListener(GameMouseMotionListener(mouseMotionInputHandler))

    val yellow = Sprite.loadImage("/sprites/tileMap.png")
    val animation = TileAnimation.fromImage(Sprite.loadImage("/sprites/tileMap.png"), 32, 32, 1.0)

    mouseMotionInputHandler.onMouseMove {
        //yellow.position = Position(it.x - 32, it.y - 32)

        animation.position = Position(it.x - 32, it.y - 32)
    }

    val gameRenderer = GameRenderer(window)
        .add(animation)
    val gameUpdater = GameUpdater()
    val gameLoop = GameLoop(gameRenderer, gameUpdater, keyInputHandler, mouseInputHandler)

    Thread(gameLoop, window.title).start()
}