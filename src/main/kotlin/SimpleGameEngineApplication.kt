import core.*
import core.input.*
import core.render.Camera
import core.render.Image
import core.render.TileAnimation
import game.Player


fun main() {

    val screen = Screen(480, 360, 2.0)
    val camera = Camera(screen)

    val keyInputHandler = GameKeyInput()
    val mouseInputHandler = GameMouseInput(2.0)

    val window = Window(screen, keyInputHandler, mouseInputHandler)
    val scene = GameScene(camera)

    val animation = TileAnimation.fromImage(Image.loadImage("/sprites/tileMap.png"), 32, 32, 1.0)
    val image = Image.loadImage("/sprites/tileMap.png")
    val player = Player(mouseInputHandler, keyInputHandler, 100.0f, image)

    scene.addGameObject(player)

    val gameLoop = GameLoop(window, scene)

    Thread(gameLoop, window.title).start()
}