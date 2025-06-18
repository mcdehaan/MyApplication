package nl.monkeysquare.myapplication.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import nl.monkeysquare.myapplication.objects.Snake

@Composable
fun SnakeComposable(snake: Snake) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val squareSize = 50f
        
        // Draw the head
        drawRoundRect(
            color = Color.Green.copy(alpha = 0.9f),
            topLeft = snake.headPosition.value,
            size = Size(squareSize, squareSize),
            cornerRadius = CornerRadius(8f)
        )
        
        // Draw all tail segments with the same color
        val tailColor = Color.Green.copy(alpha = 0.7f)
        
        snake.segments.forEach { segment ->
            drawRoundRect(
                color = tailColor,
                topLeft = segment.position.value,
                size = Size(squareSize, squareSize),
                cornerRadius = CornerRadius(8f)
            )
        }
    }
}
