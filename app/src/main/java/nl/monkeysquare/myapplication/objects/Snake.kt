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
        // First check if the head's movement would cause a wall collision
        val wallCollision = head.move()
        
        // If we already have a wall collision, return immediately
        if (wallCollision) {
            return true
        }
        
        // Move each tail segment
        tailSegments.forEachIndexed { index, segment ->
            segment.move()
        }
        
        // At this point, we know there's no wall collision
        // Check for collision with tail segments only
        return checkTailCollision()
    }
    
    // Change the direction of the snake's head
    fun setDirection(direction: SnakeHead.Direction) {
        head.setDirection(direction)
    }
    
    // Add a new tail segment when the snake eats an apple
    fun grow() {
        // Determine what the new segment should follow
        val following: Followable
        
        // If we don't have any tail segments yet, follow the head directly
        if (tailSegments.size == 0) {
            following = head
        } else {
            // Otherwise, follow the last tail segment
            following = tailSegments.last()
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
    
    // Check if the snake's head collides with any tail segment
    private fun checkTailCollision(): Boolean {
        // Skip collision check if the snake is too short
        if (tailSegments.size < 5) {
            return false
        }
        
        val headPos = head.position.value
        val headSize = 50f // Same size as in SnakeComposable
        val tailSize = headSize * 0.95f // 5% smaller as set in SnakeComposable
        
        // Only check segments that are far enough away from the head
        // The first few segments will always be close to the head
        for (i in 5 until tailSegments.size) {
            val segment = tailSegments[i]
            val segmentPos = segment.position.value
            
            // Calculate the offset applied to tail segments
            val tailOffset = (headSize - tailSize) / 2
            
            // Adjust the segment position to account for the centering offset
            val adjustedSegmentPos = Offset(
                segmentPos.x + tailOffset,
                segmentPos.y + tailOffset
            )
            
            // Check for collision (box collision)
            val collision = (
                headPos.x < adjustedSegmentPos.x + tailSize &&
                headPos.x + headSize > adjustedSegmentPos.x &&
                headPos.y < adjustedSegmentPos.y + tailSize &&
                headPos.y + headSize > adjustedSegmentPos.y
            )
            
            if (collision) {
                return true
            }
        }
        
        return false
    }
}
