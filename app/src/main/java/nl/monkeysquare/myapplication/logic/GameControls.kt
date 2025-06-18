package nl.monkeysquare.myapplication.logic

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import nl.monkeysquare.myapplication.objects.Snake
import nl.monkeysquare.myapplication.objects.SnakeHead

fun Modifier.swipeControls(snake: Snake): Modifier = this
    .pointerInput(Unit) {
        detectHorizontalDragGestures { _, dragAmount ->
            if (dragAmount > 0) {
                snake.setDirection(SnakeHead.Direction.RIGHT)
            } else {
                snake.setDirection(SnakeHead.Direction.LEFT)
            }
        }
    }
    .pointerInput(Unit) {
        detectVerticalDragGestures { _, dragAmount ->
            if (dragAmount > 0) {
                snake.setDirection(SnakeHead.Direction.DOWN)
            } else {
                snake.setDirection(SnakeHead.Direction.UP)
            }
        }
    }
