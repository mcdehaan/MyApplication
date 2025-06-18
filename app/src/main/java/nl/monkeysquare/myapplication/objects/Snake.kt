package nl.monkeysquare.myapplication.objects

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.geometry.Offset

class Snake(
    private val gameWidth: Float,
    private val gameHeight: Float,
    private val speed: Float = 10f
) {
    // Create the snake's head
    private val head = SnakeHead(gameWidth, gameHeight, speed)

    // List to store all tail segments
    private val tailSegments = mutableStateListOf<SnakeTail>()
    
    // Expose the head position for rendering
    val headPosition: MutableState<Offset> = head.position
    
    // Expose the snake's head
    val snakeHead: SnakeHead
        get() = head
    
    // Expose tail segments for rendering
    val segments = tailSegments
    
    // Move the entire snake (head and all tail segments)
    fun move(): Boolean {
        val collision = head.move()
        
        // Move each tail segment
        tailSegments.forEachIndexed { index, segment ->
            segment.move()
        }
        
        return collision
    }
    
    // Change the direction of the snake's head
    fun setDirection(direction: SnakeHead.Direction) {
        head.setDirection(direction)
    }
    
    // Add a new tail segment when the snake eats an apple
    fun grow() {
        val following = if (tailSegments.isEmpty()) {
            // First segment follows the head
            head
        } else {
            // New segment follows the last tail segment
            tailSegments.last()
        }
        
        // Create and add the new tail segment
        val newSegment = SnakeTail(following, speed)
        tailSegments.add(newSegment)
    }
    
    // Check if the snake's head collides with the apple
    fun checkAppleCollision(applePosition: Offset): Boolean {
        val snakeSize = 50f // Same size as in SnakeComposable
        val appleSize = 50f // Same size as in AppleComposable
        
        // Get the snake head's position
        val headPos = head.position.value
        
        // Check for collision (simple box collision)
        return (headPos.x < applePosition.x + appleSize &&
                headPos.x + snakeSize > applePosition.x &&
                headPos.y < applePosition.y + appleSize &&
                headPos.y + snakeSize > applePosition.y)
    }
}
