package nl.monkeysquare.myapplication.objects

import androidx.compose.ui.geometry.Offset
import kotlin.random.Random

class Apple(
    private val gameWidth: Float,
    private val gameHeight: Float
) {
    // Generate a random position for the apple within the game bounds
    val position: Offset = getRandomPosition()

    private fun getRandomPosition(): Offset {
        val x = Random.nextFloat() * gameWidth
        val y = Random.nextFloat() * gameHeight
        return Offset(x, y)
    }
}
