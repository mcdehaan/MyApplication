package nl.monkeysquare.myapplication.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import nl.monkeysquare.myapplication.objects.Snake

@Composable
fun SnakeComposable(snake: Snake) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val headSquareSize = 50f
        val tailSquareSize = headSquareSize * 0.95f  // Make tail 5% smaller than head
        
        // Draw the head
        drawRoundRect(
            color = Color.Green.copy(alpha = 0.9f),
            topLeft = snake.headPosition.value,
            size = Size(headSquareSize, headSquareSize),
            cornerRadius = CornerRadius(8f)
        )
        
        // Draw all tail segments with the same color but slightly smaller size
        val tailColor = Color.Green.copy(alpha = 0.7f)
        
        snake.segments.forEach { segment ->
            // Calculate a position adjustment to center the smaller tail segments
            val positionAdjustment = (headSquareSize - tailSquareSize) / 2
            
            drawRoundRect(
                color = tailColor,
                topLeft = Offset(
                    segment.position.value.x + positionAdjustment,
                    segment.position.value.y + positionAdjustment
                ),
                size = Size(tailSquareSize, tailSquareSize),
                cornerRadius = CornerRadius(7f)  // Slightly smaller corner radius too
            )
        }
    }
}
