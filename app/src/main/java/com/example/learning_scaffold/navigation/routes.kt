package com.example.learning_scaffold.navigation

sealed class Screen(val route : String){
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Menu : Screen("menu")
    object Setting : Screen("setting")
    object Update : Screen("update")
    object Login : Screen("login")
    object Register : Screen("register")
}