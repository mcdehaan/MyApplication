package nl.monkeysquare.myapplication.objects

import androidx.compose.runtime.MutableState
import androidx.compose.ui.geometry.Offset

class Snake(
    gameWidth: Float,
    gameHeight: Float,
    speed: Float = 10f
) {
    // Create the snake's head
    private val head = SnakeHead(gameWidth, gameHeight, speed)

    // Create the snake's tail, which follows the head
    private val tail = SnakeTail(following = head, speed = speed)

    // Expose the head and tail positions for rendering
    val headPosition: MutableState<Offset> = head.position
    val tailPosition: MutableState<Offset> = tail.position

    // Expose the snake's head
    val snakeHead: SnakeHead
        get() = head

    // Move the entire snake (both head and tail)
    fun move(): Boolean {
        val collision = head.move()
        tail.move()
        return collision
    }

    // Change the direction of the snake's head
    fun setDirection(direction: SnakeHead.Direction) {
        head.setDirection(direction)
    }
}
