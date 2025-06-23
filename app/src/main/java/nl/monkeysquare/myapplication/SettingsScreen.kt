package nl.monkeysquare.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import nl.monkeysquare.myapplication.data.GameSettingsViewModel

@Composable
fun SettingsScreen(
    navController: NavHostController, 
    settingsViewModel: GameSettingsViewModel,
    modifier: Modifier = Modifier
) {
    // Get current settings
    val settings by settingsViewModel.settings.collectAsState()
    val context = LocalContext.current
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        Text(text = "Settings", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // FPS Counter toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Show FPS Counter")
            Switch(
                checked = settings.showFpsCounter,
                onCheckedChange = { checked ->
                    settingsViewModel.updateAndSaveShowFpsCounter(context, checked)
                }
            )
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(onClick = {
            navController.navigateUp() // To navigate back to the main menu
        }) {
            Text(text = stringResource(id = R.string.menuBack))
        }
    }
}
