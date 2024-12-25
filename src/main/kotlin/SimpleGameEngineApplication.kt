import core.*
import core.input.*
import core.render.Sprite
import core.render.TileAnimation
import game.Player


fun main() {

    val screen = Screen(480, 360, 2.0)

    val keyInputHandler = GameKeyInput()
    val mouseInputHandler = GameMouseInput(2.0)

    val window = Window(screen, keyInputHandler, mouseInputHandler)
    val scene = GameScene(screen)

    val animation = TileAnimation.fromImage(Sprite.loadImage("/sprites/tileMap.png"), 32, 32, 1.0)
    val player = Player(mouseInputHandler, keyInputHandler, 100.0f, animation)

    scene.addGameObject(player)

    val gameLoop = GameLoop(window, scene)

    Thread(gameLoop, window.title).start()
}