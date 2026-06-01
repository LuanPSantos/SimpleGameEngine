package core.render

abstract class Renderable(
    var width: Int,
    var height: Int
) {

    abstract fun getPixelAt(x: Int, y: Int): Int
}