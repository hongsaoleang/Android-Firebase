package com.example.learning_scaffold.navigation.navScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.learning_scaffold.R // Make sure you have an image in res/drawable
import com.example.learning_scaffold.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenProfile(navController: NavController) {
    val context = LocalContext.current
    val settingsManager = remember { SettingsManager(context) }

    // Collect the persistent username from DataStore
    val savedName by settingsManager.userName.collectAsState(initial = "Guest")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("My Profile") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- PROFILE HEADER ---
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    .padding(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize().clip(CircleShape),
                    tint = MaterialTheme.colorScheme.outline
                )
                // If you have a real image:
                // Image(painter = painterResource(id = R.drawable.profile_placeholder), ...)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = savedName,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Student at Stanford American School",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- INFO CARD ---
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors()
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    ProfileMenuItem(
                        icon = Icons.Default.Email,
                        label = "Email",
                        subLabel = "student@stanford.edu.kh"
                    )
                    ProfileMenuItem(
                        icon = Icons.Default.Info,
                        label = "About Me",
                        subLabel = "Mobile Developer & Student"
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- QUICK ACTIONS ---
            Text(
                text = "Quick Actions",
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Button(
                onClick = { navController.navigate(Screen.Setting.route) },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.Settings, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Edit Account Settings")
            }
        }
    }
}

@Composable
fun ProfileMenuItem(icon: ImageVector, label: String, subLabel: String) {
    ListItem(
        leadingContent = { Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
        headlineContent = { Text(label, fontWeight = FontWeight.SemiBold) },
        supportingContent = { Text(subLabel) }
    )
}