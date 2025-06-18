package nl.monkeysquare.myapplication.logic

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun FPSCounter(fps: Int, modifier: Modifier = Modifier) {
    Text(
        text = "FPS: $fps",
        modifier = modifier,
        color = Color.Black
    )
}