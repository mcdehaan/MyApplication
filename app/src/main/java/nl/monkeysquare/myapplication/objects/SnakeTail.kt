package nl.monkeysquare.myapplication.objects

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset

// Create an interface for objects that can be followed
interface Followable {
    val position: MutableState<Offset>
}

// Make SnakeHead implement Followable
class SnakeTail(
    private val following: Followable,
    private val speed: Float = 10f
) : Followable {
    // Tail's position (initially follows the following object's initial position)
    override val position: MutableState<Offset> = mutableStateOf(following.position.value)

    // Update the tail's position to follow the object ahead
    fun move() {
        // The tail should move towards the position where the followed object was
        position.value = Offset(
            x = position.value.x + (following.position.value.x - position.value.x) * speed / 100,
            y = position.value.y + (following.position.value.y - position.value.y) * speed / 100
        )
    }
}
