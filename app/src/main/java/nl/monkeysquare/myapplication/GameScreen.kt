package nl.monkeysquare.myapplication

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.border
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
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
    // Get screen dimensions dynamically
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    
    // Convert screen dimensions from dp to pixels
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }
    
    // Account for UI elements (status bar, navigation bar, etc.)
    val statusBarHeight = with(density) { 24.dp.toPx() } // Approximate status bar height
    val navigationBarHeight = with(density) { 48.dp.toPx() } // Approximate navigation bar height
    val topBarHeight = with(density) { 56.dp.toPx() } // Height for back button and FPS counter
    
    // Calculate actual game area dimensions
    val gameWidth = screenWidthPx - (2 * with(density) { 2.dp.toPx() }) // Account for border width
    val gameHeight = screenHeightPx - statusBarHeight - navigationBarHeight - topBarHeight
    
    // Create a BoxWithConstraints to get the actual size after layout
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val layoutWidth = constraints.maxWidth.toFloat()
        val layoutHeight = constraints.maxHeight.toFloat()
        
        // Use these dimensions for game logic
        val actualGameWidth = layoutWidth - (2 * with(density) { 2.dp.toPx() })
        val actualGameHeight = layoutHeight - (2 * with(density) { 2.dp.toPx() })
        
        // Create game objects with the correct dimensions
        val snake = remember { Snake(actualGameWidth, actualGameHeight) }
        val apple = remember { Apple(actualGameWidth, actualGameHeight) }

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
                
                // Check for collision with apple
                if (snake.checkAppleCollision(apple.position.value)) {
                    // Grow the snake
                    snake.grow()
                    
                    // Respawn the apple
                    apple.respawn()
                    
                    // Increase score
                    score++
                }
                
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

        Box(modifier = Modifier
            .fillMaxSize()
            .border(width = 2.dp, color = Color.Black)
            .swipeControls(snake.snakeHead)) {
            BackButton(navController, Modifier.align(Alignment.TopStart).padding(16.dp))
            FPSCounter(fps, Modifier.align(Alignment.TopEnd).padding(16.dp))
            SnakeComposable(snake)
            AppleComposable(apple)
        }
    }
}
