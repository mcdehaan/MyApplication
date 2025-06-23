package nl.monkeysquare.myapplication.data

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

// Extension to create a DataStore instance for preferences
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 * Data class that holds game settings
 */
data class GameSettings(
    val showFpsCounter: Boolean = false
)

/**
 * ViewModel to manage game settings across the app
 */
class GameSettingsViewModel : ViewModel() {
    // Internal mutable state
    private val _settings = MutableStateFlow(GameSettings())
    
    // Expose settings as read-only StateFlow
    val settings: StateFlow<GameSettings> = _settings.asStateFlow()

    // Preference keys
    companion object {
        val SHOW_FPS_COUNTER = booleanPreferencesKey("show_fps_counter")
    }

    // Update showFpsCounter setting
    fun updateShowFpsCounter(show: Boolean) {
        _settings.value = _settings.value.copy(showFpsCounter = show)
    }

    // Load settings from DataStore
    suspend fun loadSettings(context: Context) {
        context.dataStore.data.first().let { preferences ->
            val showFpsCounter = preferences[SHOW_FPS_COUNTER] ?: false
            _settings.value = GameSettings(showFpsCounter = showFpsCounter)
        }
    }

    // Save settings to DataStore
    suspend fun saveSettings(context: Context) {
        context.dataStore.edit { preferences ->
            preferences[SHOW_FPS_COUNTER] = _settings.value.showFpsCounter
        }
    }

    // Update and save settings in one operation
    fun updateAndSaveShowFpsCounter(context: Context, show: Boolean) {
        updateShowFpsCounter(show)
        viewModelScope.launch(Dispatchers.IO) {
            saveSettings(context)
        }
    }
}
