package com.example.learning_scaffold.navigation.navScreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.learning_scaffold.auth.AuthManager
import com.example.learning_scaffold.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffoldScreen(
    navController: NavController, 
    authManager: AuthManager,
    content: @Composable (PaddingValues) -> Unit
) {
    val mySnackbarHostState = remember { SnackbarHostState() }
    
    // Get current route to determine if we should show the Scaffold bars
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    // Auth routes should not show the Scaffold bars
    val authRoutes = listOf(Screen.Login.route, Screen.Register.route)
    val isAuthRoute = currentRoute == null || currentRoute in authRoutes

    if (isAuthRoute) {
        // Just show the content without any bars for Login/Register
        content(PaddingValues(0.dp))
    } else {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(text = "My Scaffold") },
                    actions = {
                        // ADD Button
                        IconButton(
                            onClick = {
                                // Navigate to Home where the "Add Student" section is
                                navController.navigate(Screen.Home.route) {
                                    launchSingleTop = true
                                }
                            }
                        ){
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                modifier = Modifier.size(30.dp)
                            )
                        }
                        
                        // LOGOUT Button
                        IconButton(
                            onClick = {
                                authManager.logout()
                                navController.navigate(Screen.Login.route) {
                                    // Clear backstack so user can't go back to Home
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        ){
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = "Logout",
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
                        selected = currentRoute == Screen.Setting.route,
                        onClick = { navController.navigate(Screen.Setting.route) { 
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true 
                            restoreState = true
                        } },
                        icon = { Icon(Icons.Default.Settings, contentDescription = null) },
                        label = { Text("Settings") }
                    )
                    // Home
                    NavigationBarItem(
                        selected = currentRoute == Screen.Home.route,
                        onClick = { navController.navigate(Screen.Home.route) { 
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true 
                            restoreState = true
                        } },
                        icon = { Icon(Icons.Default.Home, contentDescription = null) },
                        label = { Text("Home") }
                    )
                    // Profile
                    NavigationBarItem(
                        selected = currentRoute == Screen.Profile.route,
                        onClick = { navController.navigate(Screen.Profile.route) { 
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true 
                            restoreState = true
                        } },
                        icon = { Icon(Icons.Default.Person, contentDescription = null) },
                        label = { Text("Profile") }
                    )
                    // Menu/List
                    NavigationBarItem(
                        selected = currentRoute == Screen.Menu.route,
                        onClick = { navController.navigate(Screen.Menu.route) { 
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true 
                            restoreState = true
                        } },
                        icon = { Icon(Icons.Default.List, contentDescription = null) },
                        label = { Text("Menu") }
                    )
                }
            }
        ) { myPadding ->
            content(myPadding)
        }
    }
}