package com.example.learning_scaffold.navigation.navScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenSetting(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val settingsManager = remember { SettingsManager(context) }

    val isDarkMode by settingsManager.isDarkMode.collectAsState(initial = false)
    val savedName by settingsManager.userName.collectAsState(initial = "")

    Scaffold(
        topBar = { TopAppBar(title = { Text("Settings") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(
                "Appearance",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )
            // Standard List Item for Dark Mode
            ListItem(
                headlineContent = { Text("Dark Mode") },
                supportingContent = { Text("Change app theme to dark or light") },
                trailingContent = {
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { scope.launch { settingsManager.setDarkMode(it) } }
                    )
                }
            )

            HorizontalDivider()
            Text(
                "Profile",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            // Input field in a more standard "Account" look
            ListItem(
                headlineContent = { Text("Display Name") },
                supportingContent = {
                    OutlinedTextField(
                        value = savedName,
                        onValueChange = { scope.launch { settingsManager.setUserName(it) } },
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                        placeholder = { Text("Enter name") }
                    )
                }
            )
        }
    }
}