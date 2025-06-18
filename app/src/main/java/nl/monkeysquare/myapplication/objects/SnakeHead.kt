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
) : Followable {
    enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }

    // Initialize snake's position in a safer location within the game area
    private val initialPosition = getSafeStartPosition()
    override val position: MutableState<Offset> = mutableStateOf(initialPosition)
    private var currentDirection: Direction by mutableStateOf(getInitialDirection(initialPosition))

    // Update snake's position based on the current direction
    fun move(): Boolean {
        // First calculate the new position based on direction
        val newPosition = when (currentDirection) {
            Direction.UP -> position.value.copy(y = position.value.y - speed)
            Direction.DOWN -> position.value.copy(y = position.value.y + speed)
            Direction.LEFT -> position.value.copy(x = position.value.x - speed)
            Direction.RIGHT -> position.value.copy(x = position.value.x + speed)
        }
        
        // Check if the new position is out of bounds
        if (isOutOfBounds(newPosition)) {
            return true // Collision detected
        }
        
        // Update position if no collision
        position.value = newPosition
        return false // No collision
    }
    
    // Check if the position is out of bounds
    private fun isOutOfBounds(pos: Offset): Boolean {
        val squareSize = 50f // Same size as in SnakeComposable
        
        return pos.x < 0 || pos.x + squareSize > gameWidth ||
               pos.y < 0 || pos.y + squareSize > gameHeight
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

    // Generate a safe starting position away from edges
    private fun getSafeStartPosition(): Offset {
        // Place the snake in the middle third of the screen
        val safeMinX = gameWidth * 0.33f
        val safeMaxX = gameWidth * 0.67f
        val safeMinY = gameHeight * 0.33f
        val safeMaxY = gameHeight * 0.67f
        
        val x = safeMinX + (safeMaxX - safeMinX) * Random.nextFloat()
        val y = safeMinY + (safeMaxY - safeMinY) * Random.nextFloat()
        
        return Offset(x, y)
    }
    
    // Determine the best initial direction based on position
    private fun getInitialDirection(pos: Offset): Direction {
        val centerX = gameWidth / 2
        val centerY = gameHeight / 2
        
        // Move toward the center of the screen
        return when {
            pos.x < centerX && pos.y < centerY -> {
                // Top-left quadrant - move right or down
                if (Random.nextBoolean()) Direction.RIGHT else Direction.DOWN
            }
            pos.x >= centerX && pos.y < centerY -> {
                // Top-right quadrant - move left or down
                if (Random.nextBoolean()) Direction.LEFT else Direction.DOWN
            }
            pos.x < centerX && pos.y >= centerY -> {
                // Bottom-left quadrant - move right or up
                if (Random.nextBoolean()) Direction.RIGHT else Direction.UP
            }
            else -> {
                // Bottom-right quadrant - move left or up
                if (Random.nextBoolean()) Direction.LEFT else Direction.UP
            }
        }
    }
}
