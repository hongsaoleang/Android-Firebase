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
import com.example.learning_scaffold.auth.AuthManager
import com.example.learning_scaffold.navigation.myNavigation
import com.example.learning_scaffold.navigation.navScreen.SettingsManager
import com.example.learning_scaffold.ui.theme.Learning_ScaffoldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Enables drawing behind the status/navigation bars
        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current

            // 2. Initialize your Managers. 'remember' ensures they stay
            // alive during configuration changes (like rotating the screen).
            val settingsManager = remember { SettingsManager(context) }
            val authManager = remember { AuthManager() }

            // 3. Observe the theme state from your DataStore/Preferences
            val isDarkMode by settingsManager.isDarkMode.collectAsState(initial = false)

            Learning_ScaffoldTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 4. THIS IS THE KEY: Run the navigation function.
                    // It will look at its 'startDestination' (login) and
                    // show the LoginScreen first.
                    myNavigation(authManager = authManager)
                }
            }
        }
    }
}