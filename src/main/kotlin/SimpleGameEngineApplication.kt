import core.*
import core.input.*
import core.math.Vector2
import core.render.Camera
import core.render.Image
import core.render.TileAnimation
import game.CameraController
import game.Player


fun main() {

    val screen = Screen(480, 360, 2.0)
    val camera = Camera(screen)

    val keyInputHandler = GameKeyInput()
    val mouseInputHandler = GameMouseInput(2.0)

    val window = Window(screen, keyInputHandler, mouseInputHandler)
    val scene = GameScene(camera, screen)

    val animation = TileAnimation.fromImage(Image.loadImage("/sprites/tileMap.png"), 32, 32, 1.0)
    val image = Image.loadImage("/sprites/tileMap.png")
    val player = Player(mouseInputHandler, keyInputHandler, 100.0f, image)
    player.transform.scale = Vector2(2f,2f)

    scene.addGameObject(player)
    scene.addGameObject(CameraController(camera, 100f, keyInputHandler))

    val gameLoop = GameLoop(window, scene)

    Thread(gameLoop, window.title).start()
}