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
        
        // Draw all tail segments
        snake.segments.forEachIndexed { index, segment ->
            // Make each segment a slightly different shade of green
            val segmentColor = Color.Green.copy(
                alpha = 0.8f - (index * 0.05f).coerceAtLeast(0.3f)
            )
            
            drawRoundRect(
                color = segmentColor,
                topLeft = segment.position.value,
                size = Size(squareSize, squareSize),
                cornerRadius = CornerRadius(8f)
            )
        }
    }
}
