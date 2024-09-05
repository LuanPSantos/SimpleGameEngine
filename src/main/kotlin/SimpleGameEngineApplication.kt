import core.*
import core.input.*
import core.render.Renderer
import game.Player


fun main() {
    val window = Window(480, 360, 2.0)
    val keyInputHandler = GameKeyEventHandler()
    val mouseInputHandler = GameMouseInput(2.0)

    window.addCanvasKeyListener(GameKeyListener(keyInputHandler))
    window.addCanvasMouseListener(GameMouseListener(mouseInputHandler))

    val scene = GameScene(Renderer(window))
        .addGameObject(Player(mouseInputHandler))

    val gameLoop = GameLoop(scene, keyInputHandler, mouseInputHandler)

    Thread(gameLoop, window.title).start()
}