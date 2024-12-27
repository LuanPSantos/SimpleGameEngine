package core.render

abstract class Renderable(
    val width: Int,
    val height: Int
) {

    abstract fun getPixelAt(x: Int, y: Int): Int
}