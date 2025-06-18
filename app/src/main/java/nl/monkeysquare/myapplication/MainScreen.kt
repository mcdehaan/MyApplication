package nl.monkeysquare.myapplication

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MainScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val title = stringResource(id = R.string.title)
    var text by remember { mutableStateOf("Loading") }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        text = title
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            fontSize = MaterialTheme.typography.headlineMedium.fontSize // equivalent to 24sp
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Start Button
        Button(onClick = {
            navController.navigate("game_screen")
        }) {
            Text(text = stringResource(id = R.string.start_game))
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Settings Button
        Button(onClick = {
            navController.navigate("settings_screen")
        }) {
            Text(text = stringResource(id = R.string.settings))
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Exit Button
        Button(onClick = {
            (context as? ComponentActivity)?.finish()
        }) {
            Text(text = stringResource(id = R.string.exit_game))
        }
    }
}
