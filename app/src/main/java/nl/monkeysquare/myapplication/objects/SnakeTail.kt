package nl.monkeysquare.myapplication.objects

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset

// Create an interface for objects that can be followed
interface Followable {
    val position: MutableState<Offset>
    // Add method to get the historical positions
    fun getPositionHistory(): List<Offset>
}

// Make SnakeHead implement Followable
class SnakeTail(
    private val following: Followable,
    private val speed: Float = 10f
) : Followable {
    // Tail's position (initially follows the following object's initial position)
    override val position: MutableState<Offset> = mutableStateOf(following.position.value)
    
    // Track the path of positions this segment has been in
    private val positionHistory = mutableStateListOf<Offset>()
    
    // The distance this segment should maintain from the object it follows
    private val followDistance = speed
    
    init {
        // Initialize with current position
        positionHistory.add(position.value)
    }

    // Update the tail's position to follow the object ahead, but only in cardinal directions
    fun move() {
        // Get the position history of the object we're following
        val followingHistory = following.getPositionHistory()
        
        // If there's history available and we need to move
        if (followingHistory.isNotEmpty()) {
            // Calculate the total path length we should be behind the following segment
            val targetIndex = findTargetPositionIndex(followingHistory)
            
            if (targetIndex >= 0) {
                // Move to the exact position the followed segment was at previously
                position.value = followingHistory[targetIndex]
            }
        }
        
        // Record our new position in history
        positionHistory.add(0, position.value)
        
        // Keep history at a reasonable size
        if (positionHistory.size > 100) { // arbitrary limit to avoid memory issues
            positionHistory.removeAt(positionHistory.size - 1)
        }
    }
    
    // Find the correct historical position to move to
    private fun findTargetPositionIndex(followingHistory: List<Offset>): Int {
        // Simple implementation: follow at a fixed distance (index)
        // In a proper snake game, this could be calculated based on segment length
        val targetIndex = 5 // This creates the spacing between segments
        return if (targetIndex < followingHistory.size) targetIndex else followingHistory.size - 1
    }
    
    // Implement the interface method to provide position history
    override fun getPositionHistory(): List<Offset> {
        return positionHistory
    }
}
