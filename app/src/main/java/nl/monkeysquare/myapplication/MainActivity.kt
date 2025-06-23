package nl.monkeysquare.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nl.monkeysquare.myapplication.data.GameSettingsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Initialize settings
        val coroutineScope = CoroutineScope(Dispatchers.Main)
        
        setContent {
            val navController = rememberNavController()
            val settingsViewModel: GameSettingsViewModel = viewModel()
            
            // Load settings when the app starts
            coroutineScope.launch {
                settingsViewModel.loadSettings(applicationContext)
            }
            
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "main_menu",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("main_menu") { MainScreen(navController) }
                    composable("settings_screen") { 
                        SettingsScreen(navController, settingsViewModel) 
                    }
                    composable("game_screen") { 
                        GameScreen(navController, settingsViewModel) 
                    }
                    composable("game_over_screen/{score}") { backStackEntry ->
                        val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
                        GameOverScreen(navController, score)
                    }
                }
            }
        }
    }
}
