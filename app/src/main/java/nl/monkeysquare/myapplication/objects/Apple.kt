package nl.monkeysquare.myapplication.objects

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import kotlin.random.Random

class Apple(
    private val gameWidth: Float,
    private val gameHeight: Float
) {
    // Generate a random position for the apple within the game bounds
    val position: MutableState<Offset> = mutableStateOf(getRandomPosition())

    // Respawn the apple in a new random position
    fun respawn() {
        position.value = getRandomPosition()
    }

    private fun getRandomPosition(): Offset {
        val x = Random.nextFloat() * (gameWidth - 50f) // Subtract apple size to keep within bounds
        val y = Random.nextFloat() * (gameHeight - 50f)
        return Offset(x, y)
    }
}
