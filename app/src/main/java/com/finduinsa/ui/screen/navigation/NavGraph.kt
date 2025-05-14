package com.finduinsa.ui.screen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.finduinsa.ui.screen.auth.LoginScreen
import com.finduinsa.ui.screen.auth.RegisterScreen
import com.finduinsa.ui.screen.mainmenu.MainMenuScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }
        composable(Screen.MainMenu.route) {
            MainMenuScreen()
        }
    }
}
