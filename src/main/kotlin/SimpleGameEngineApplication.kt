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
        Image(
            2, 2,
            arrayOf(
                intArrayOf(Color.GREEN.rgb, Color.GREEN.rgb),
                intArrayOf(Color.GREEN.rgb, Color.GREEN.rgb)
            )
        ),
        Image(
            2, 2,
            arrayOf(
                intArrayOf(Color.YELLOW.rgb, Color.YELLOW.rgb),
                intArrayOf(Color.YELLOW.rgb, Color.YELLOW.rgb)
            )
        ),
        Image(
            2, 2,
            arrayOf(
                intArrayOf(Color.RED.rgb, Color.RED.rgb),
                intArrayOf(Color.RED.rgb, Color.RED.rgb)
            )
        ),
        Image(
            2, 2,
            arrayOf(
                intArrayOf(Color.BLUE.rgb, Color.BLUE.rgb),
                intArrayOf(Color.BLUE.rgb, Color.BLUE.rgb)
            )
        )
    )


    for (i in 0..300) {
        scene.addGameObject(Player(Random.nextDouble(0.1, 1.0), images[Random.nextInt(images.size)]))
    }


    scene.addGameObject(CameraController(camera, 100f, keyInputHandler))

    val gameLoop = GameLoop(window, scene)

    Thread(gameLoop, window.title).start()
}