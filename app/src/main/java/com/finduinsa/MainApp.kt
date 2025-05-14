package com.finduinsa

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finduinsa.ui.screen.auth.LoginScreen
import com.finduinsa.ui.screen.auth.RegisterScreen
import com.finduinsa.ui.screen.mainmenu.MainMenuScreen
import com.finduinsa.ui.screen.navigation.Screen

@Composable
fun MainApp() {
    val navController = rememberNavController()

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
