import core.*
import core.Window.Screen
import core.input.*
import core.math.Position
import core.render.GameRenderer
import core.update.GameUpdater
import game.Sprite
import java.awt.event.MouseEvent


fun main() {
    val window = Window(Screen(960, 720, scale = 1.0))

    val keyInputHandler = GameKeyEventHandler()
    val mouseInputHandler = GameMouseEventHandler()
    val mouseMotionInputHandler = GameMouseMotionEventHandler(window.screen.scale)

    window.addCanvasKeyListener(GameKeyListener(keyInputHandler))
    window.addCanvasMouseListener(GameMouseListener(mouseInputHandler))
    window.addCanvasMouseMotionListener(GameMouseMotionListener(mouseMotionInputHandler))

    val orange = Sprite.loadImage("/sprites/square.png")
    val green = Sprite.loadImage("/sprites/green.png")
    val yellow = Sprite.loadImage("/sprites/yellow.png")

    mouseMotionInputHandler.onMouseMove {
        yellow.position = Position(it.x - 32, it.y- 32)
    }

    val gameRenderer = GameRenderer(window)
        .add(orange)
        .add(green)
        .add(yellow)
    val gameUpdater = GameUpdater()
    val gameLoop = GameLoop(gameRenderer, gameUpdater, keyInputHandler, mouseInputHandler)

    Thread(gameLoop, window.title).start()
}