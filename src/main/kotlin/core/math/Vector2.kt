package core.math

class Vector2<T>(
    val x: T,
    val y: T
) {
    override fun toString(): String {
        return "x: $x, y: $y"
    }
}