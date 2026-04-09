package com.example.learning_scaffold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.learning_scaffold.navigation.myNavigation
import com.example.learning_scaffold.navigation.navScreen.SettingsManager
import com.example.learning_scaffold.ui.theme.Learning_ScaffoldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current
            // Initialize SettingsManager to read saved state
            val settingsManager = remember { SettingsManager(context) }

            // Collect the Dark Mode state (defaults to false/light)
            val isDarkMode by settingsManager.isDarkMode.collectAsState(initial = false)

            // Wrap the entire app in the Theme and pass the state
            Learning_ScaffoldTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    myNavigation()
                }
            }
        }
    }
}