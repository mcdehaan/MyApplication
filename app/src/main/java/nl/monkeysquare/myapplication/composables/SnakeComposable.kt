package nl.monkeysquare.myapplication.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import nl.monkeysquare.myapplication.objects.SnakeHead

@Composable
fun SnakeComposable(snake: SnakeHead) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val squareSize = 50f

        drawRoundRect(
            color = Color.Green,
            topLeft = snake.position.value,
            size = Size(squareSize, squareSize),
            cornerRadius = CornerRadius(8f)
        )
    }
}
