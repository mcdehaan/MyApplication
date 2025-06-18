package nl.monkeysquare.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun GameOverScreen(navController: NavHostController, score: Int = 0, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "GAME OVER",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.error
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "Score: $score",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { navController.navigate("game_screen") {
                    popUpTo("game_screen") { inclusive = true }
                }},
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Play Again")
            }
            
            Button(
                onClick = { navController.navigate("main_menu") {
                    popUpTo("main_menu") { inclusive = true }
                }},
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Main Menu")
            }
        }
    }
}
