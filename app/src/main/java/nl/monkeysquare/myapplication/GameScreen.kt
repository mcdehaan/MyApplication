package nl.monkeysquare.myapplication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

import nl.monkeysquare.myapplication.composables.AppleComposable
import nl.monkeysquare.myapplication.composables.SnakeComposable
import nl.monkeysquare.myapplication.logic.BackButton
import nl.monkeysquare.myapplication.logic.FPSCounter
import nl.monkeysquare.myapplication.logic.swipeControls
import nl.monkeysquare.myapplication.objects.Apple
import nl.monkeysquare.myapplication.objects.Snake

@Composable
fun GameScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val gameWidth = 1080f
    val gameHeight = 1920f

    // Create an instance of the Snake with a random starting position
    val snake = remember { Snake(gameWidth, gameHeight) }

    // Create an instance of the Apple with random position
    val apple = remember { Apple(gameWidth, gameHeight) }

    var score by remember { mutableIntStateOf(0) }
    var gameOver by remember { mutableStateOf(false) }

    var totalTime by remember { mutableLongStateOf(0L) }
    var frameCount by remember { mutableIntStateOf(0) }
    val fps by remember {
        derivedStateOf { if (totalTime > 0) (frameCount * 1000 / totalTime).toInt() else 0 }
    }

    LaunchedEffect(Unit) {
        while (true) {
            if (gameOver) break
            
            val frameStartTime = System.currentTimeMillis()
            val collision = snake.move()
            
            if (collision) {
                gameOver = true
                navController.navigate("game_over_screen/${score}")
                break
            }
            
            val frameTime = System.currentTimeMillis() - frameStartTime
            totalTime += frameTime
            frameCount++

            if (totalTime >= 1000L) {
                totalTime = 0L
                frameCount = 0
            }

            val frameDuration = System.currentTimeMillis() - frameStartTime
            val delayTime = (16L - frameDuration).coerceAtLeast(0L)
            delay(delayTime)
        }
    }

    Box(modifier = modifier
        .fillMaxSize()
        .swipeControls(snake.snakeHead)) {
        BackButton(navController, Modifier.align(Alignment.TopStart).padding(16.dp))
        FPSCounter(fps, Modifier.align(Alignment.TopEnd).padding(16.dp))
        SnakeComposable(snake.snakeHead)
        AppleComposable(apple)
    }
}
