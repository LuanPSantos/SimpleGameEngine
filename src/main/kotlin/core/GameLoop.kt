package core

import core.input.GameKeyEventHandler
import core.input.GameMouseEventHandler
import core.render.GameRenderer
import core.update.GameUpdater

class GameLoop(
    private val gameRenderer: GameRenderer,
    private val gameUpdater: GameUpdater,
    private val keyInputHandler: GameKeyEventHandler,
    private val mouseInputHandler: GameMouseEventHandler
) : Runnable {

    private var running = false
    private var rendering = false

    private var startTime = 0.0
    private var endTime = 0.0
    private var passedTime = 0.0
    private var unprocessedTime = 0.0

    private var frameTime = 0.0
    private var frames = 0
    private var fps = 0

    override fun run() {
        startTime = getCurrentNormalizedTime()
        running = true

        while (running) {
            rendering = false

            endTime = getCurrentNormalizedTime()
            passedTime = endTime - startTime
            unprocessedTime += passedTime
            frameTime += passedTime
            startTime = endTime

            while (unprocessedTime > DELTA_TIME) {
                gameUpdater.update()

                unprocessedTime -= DELTA_TIME
                rendering = true
                if (frameTime > ONE_SECOND) {
                    frameTime = 0.0
                    fps = frames
                    frames = 0

                    println("FPS: $fps")
                }
            }

            if (rendering) {
                gameRenderer.render()

                rendering = false
                frames++
            } else {
                Thread.sleep(ONE_MILLIS)
            }

            keyInputHandler.update()
            mouseInputHandler.update()
        }

        dispose()
    }

    private fun dispose() {
        println("dispose")
    }

    private fun getCurrentNormalizedTime(): Double {
        return System.nanoTime() / ONE_BILLION
    }

    companion object {
        const val DELTA_TIME = 1.0 / 60.0
        const val ONE_MILLIS = 1L
        const val ONE_BILLION = 1_000_000_000.0
        const val ONE_SECOND = 1
    }
}