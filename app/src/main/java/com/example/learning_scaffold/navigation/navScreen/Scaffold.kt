package com.example.learning_scaffold.navigation.navScreen

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import com.example.learning_scaffold.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffoldScreen(navController: NavController, content: @Composable (PaddingValues) -> Unit) {
    val mySnackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "My Scaffold") },
                actions = {
                    // This button now goes to the Menu/List screen
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.Menu.route)
                        }
                    ){
                        Icon(
                            imageVector = Icons.Default.List, // Changed to List icon
                            contentDescription = "Go to List",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.Setting.route)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountBox,
                            contentDescription = "Settings",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                },
            )
        },
        snackbarHost = { SnackbarHost(hostState = mySnackbarHostState) },
        bottomBar = {
            NavigationBar {
                // Settings
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Setting.route) { launchSingleTop = true } },
                    icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                    label = { Text("Settings") }
                )
                // Home
                NavigationBarItem(
                    selected = true,
                    onClick = { navController.navigate(Screen.Home.route) { launchSingleTop = true } },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Home") }
                )
                // Profile
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Profile.route) { launchSingleTop = true } },
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    label = { Text("Profile") }
                )
                // Menu/List
                NavigationBarItem(
                    selected = false,
                    onClick = { navController.navigate(Screen.Menu.route) { launchSingleTop = true } },
                    icon = { Icon(Icons.Default.List, contentDescription = null) },
                    label = { Text("Menu") }
                )
            }
        }
    ) { myPadding ->
        content(myPadding)
    }
}