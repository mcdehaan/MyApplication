package nl.monkeysquare.myapplication.logic

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import nl.monkeysquare.myapplication.R

@Composable
fun BackButton(navController: NavHostController, modifier: Modifier = Modifier) {
    IconButton(
        onClick = { navController.navigateUp() },
        modifier = modifier
    ) {
        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(id = R.string.gameBack))
    }
}
