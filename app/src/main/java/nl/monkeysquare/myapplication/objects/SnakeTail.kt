package nl.monkeysquare.myapplication.objects

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset

class SnakeTail(
    private val following: SnakeHead,
    private val speed: Float = 10f
) {
    // Tail's position (initially follows the head's initial position)
    val position: MutableState<Offset> = mutableStateOf(following.position.value)

    // Update the tail's position to follow the head
    fun move() {
        // The tail should move towards the position where the head was
        position.value = Offset(
            x = position.value.x + (following.position.value.x - position.value.x) * speed / 100,
            y = position.value.y + (following.position.value.y - position.value.y) * speed / 100
        )
    }
}
