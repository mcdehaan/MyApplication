package nl.monkeysquare.myapplication.objects

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import kotlin.random.Random

class SnakeHead(
    private val gameWidth: Float,
    private val gameHeight: Float,
    private val speed: Float = 10f // Increased speed for better control
) {
    enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }

    // Initialize snake's position randomly within the game area
    val position: MutableState<Offset> = mutableStateOf(getRandomPosition())
    private var currentDirection: Direction by mutableStateOf(Direction.RIGHT)

    // Update snake's position based on the current direction
    fun move() {
        position.value = when (currentDirection) {
            Direction.UP -> position.value.copy(y = position.value.y - speed)
            Direction.DOWN -> position.value.copy(y = position.value.y + speed)
            Direction.LEFT -> position.value.copy(x = position.value.x - speed)
            Direction.RIGHT -> position.value.copy(x = position.value.x + speed)
        }
    }

    // Change the direction of the snake
    fun setDirection(newDirection: Direction) {
        // Prevent the snake from reversing direction directly
        if ((currentDirection == Direction.UP && newDirection != Direction.DOWN) ||
            (currentDirection == Direction.DOWN && newDirection != Direction.UP) ||
            (currentDirection == Direction.LEFT && newDirection != Direction.RIGHT) ||
            (currentDirection == Direction.RIGHT && newDirection != Direction.LEFT)
        ) {
            currentDirection = newDirection
        }
    }

    // Generate a random position within the game bounds
    private fun getRandomPosition(): Offset {
        val x = Random.nextFloat() * gameWidth
        val y = Random.nextFloat() * gameHeight
        return Offset(x, y)
    }
}
