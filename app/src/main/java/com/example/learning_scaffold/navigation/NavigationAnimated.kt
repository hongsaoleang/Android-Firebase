package com.example.learning_scaffold.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.learning_scaffold.auth.AuthManager
import com.example.learning_scaffold.navigation.navScreen.MyScaffoldScreen
import com.example.learning_scaffold.navigation.navScreen.ScreenHome
import com.example.learning_scaffold.navigation.navScreen.ScreenMenu
import com.example.learning_scaffold.navigation.navScreen.ScreenProfile
import com.example.learning_scaffold.navigation.navScreen.ScreenSetting
import com.example.learning_scaffold.navigation.navScreen.UpdateScreen
import com.example.learning_scaffold.navigation.navScreen.auth.LoginScreen
import com.example.learning_scaffold.navigation.navScreen.auth.RegisterScreen

@Composable
fun myNavigation(authManager: AuthManager) {
    val navController = rememberNavController()
    
    // Pass authManager to MyScaffoldScreen so it can handle Logout
    MyScaffoldScreen(navController = navController, authManager = authManager) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Login.route) {
                LoginScreen(navController, authManager)
            }
            composable(Screen.Register.route) {
                RegisterScreen(navController, authManager)
            }
            composable(Screen.Home.route) {
                ScreenHome(navController)
            }
            composable(Screen.Profile.route) {
                ScreenProfile(navController)
            }
            composable(Screen.Setting.route) {
                ScreenSetting(navController)
            }
            composable(Screen.Menu.route) {
                ScreenMenu(navController)
            }
            // Corrected the route parameters and the UpdateScreen call to use "phone" instead of "salary"
            composable("update/{id}/{name}/{age}/{phone}/{gender}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                val name = backStackEntry.arguments?.getString("name") ?: ""
                val age = backStackEntry.arguments?.getString("age")?.toIntOrNull() ?: 0
                val phone = backStackEntry.arguments?.getString("phone") ?: ""
                val gender = backStackEntry.arguments?.getString("gender") ?: ""

                UpdateScreen(
                    navController = navController,
                    documentId = id,
                    initialName = name,
                    initialAge = age,
                    initialPhone = phone,
                    initialGender = gender
                )
            }
        }
    }
}