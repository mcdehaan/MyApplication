package nl.monkeysquare.myapplication

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
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
import nl.monkeysquare.myapplication.data.GameSettingsViewModel

@Composable
fun GameScreen(
    navController: NavHostController,
    settingsViewModel: GameSettingsViewModel,
    modifier: Modifier = Modifier
) {
    // Get the current density
    val density = LocalDensity.current
    
    // Get current settings
    val settings by settingsViewModel.settings.collectAsState()
    
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
            while (!gameOver) {
                val frameStartTime = System.currentTimeMillis()
                
                // Move the snake and check for collisions
                val collision = snake.move()
                if (collision) {
                    gameOver = true
                    navController.navigate("game_over_screen/$score")
                    break
                }
                
                // Check if the snake eats the apple
                if (snake.checkAppleCollision(apple.position.value)) {
                    // Move the apple to a new random position
                    apple.respawn()
                    
                    // Grow the snake
                    snake.grow()
                    
                    // Increase the score
                    score++
                }
                
                // Update FPS calculation
                totalTime += System.currentTimeMillis() - frameStartTime
                frameCount++
                
                // Cap frame rate to about 60 FPS
                val frameDuration = System.currentTimeMillis() - frameStartTime
                val delayTime = (16L - frameDuration).coerceAtLeast(0L)
                delay(delayTime)
            }
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .border(width = 2.dp, color = Color.Black)
            .swipeControls(snake)) {
            // Back button at top left
            BackButton(navController, Modifier.align(Alignment.TopStart).padding(16.dp))
            
            // Only show FPS counter if enabled in settings
            if (settings.showFpsCounter) {
                FPSCounter(fps, Modifier.align(Alignment.TopEnd).padding(16.dp))
            }
            
            // Game elements
            SnakeComposable(snake)
            AppleComposable(apple)
        }
    }
}
