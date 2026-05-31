import core.*
import core.input.*
import core.math.Vector2
import core.render.Camera
import core.render.Image
import core.render.TileAnimation
import game.CameraController
import game.Player
import java.awt.Color
import kotlin.random.Random


fun main() {

    val screen = Screen(480, 360, 2.0)
    val camera = Camera(screen)

    val keyInputHandler = GameKeyInput()
    val mouseInputHandler = GameMouseInput(2.0)

    val window = Window(screen, keyInputHandler, mouseInputHandler)
    val scene = GameScene.new(camera, screen)

    val images = arrayOf(
        Color.WHITE.rgb,
        Color.GREEN.rgb,
        Color.BLUE.rgb,
        Color.RED.rgb,
        Color.CYAN.rgb,
        Color.ORANGE.rgb,
        Color.PINK.rgb,
        Color.YELLOW.rgb
    ).map { Image(
        1, 1,
        arrayOf(
            intArrayOf(it)
        )
    ) }


    for(i in 0..100) {
        scene.addGameObject(Player(Random.nextDouble(10.0, 50.0), images[Random.nextInt(images.size)], mouseInputHandler))
    }



    scene.addGameObject(CameraController(camera, mouseInputHandler))

    val gameLoop = GameLoop(window, scene)

    Thread(gameLoop, window.title).start()
}