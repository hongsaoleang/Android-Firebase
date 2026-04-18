package com.example.learning_scaffold.navigation

import com.example.learning_scaffold.navigation.navScreen.ScreenSetting
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.learning_scaffold.navigation.navScreen.MyScaffoldScreen
import com.example.learning_scaffold.navigation.navScreen.ScreenHome
import com.example.learning_scaffold.navigation.navScreen.ScreenMenu
import com.example.learning_scaffold.navigation.navScreen.ScreenProfile
import com.example.learning_scaffold.navigation.navScreen.UpdateScreen

@Composable
fun myNavigation(){
    val  navController = rememberNavController()
    MyScaffoldScreen(navController = navController) {
        PrimePadding-> NavHost(
        navController = navController ,
        startDestination =  "home",
        modifier = Modifier.padding(PrimePadding)
    ){
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
        composable("update/{id}/{name}/{age}/{salary}/{gender}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val age = backStackEntry.arguments?.getString("age")?.toIntOrNull() ?: 0
            val salary = backStackEntry.arguments?.getString("salary")?.toString() ?: ""
            val gender = backStackEntry.arguments?.getString("gender") ?: ""

            UpdateScreen(
                navController = navController,
                documentId = id,
                initialName = name,
                initialAge = age,
                initialSalary = salary,
                initialGender = gender
            )
        }
    }
    }

}