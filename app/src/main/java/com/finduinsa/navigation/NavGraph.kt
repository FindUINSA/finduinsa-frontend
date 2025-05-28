package com.finduinsa.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.finduinsa.presentation.foundreport.FoundReportScreen
import com.finduinsa.presentation.home.HomeScreen
import com.finduinsa.ui.screens.LostReport.LostReportScreen
import com.finduinsa.ui.screens.auth.LoginScreen
import com.finduinsa.ui.screens.auth.RegisterScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Login.route) { // PERUBAHAN DI SINI
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.LostReport.route) {
            LostReportScreen(navController)
        }
        composable(Screen.FoundReport.route) {
            FoundReportScreen(navController)
        }
        // Anda bisa menambahkan composable untuk fitur lain jika ada:
        composable(Screen.Messages.route) {
            // Tampilan untuk pesan (TODO)
        }
        composable(Screen.Notifications.route) {
            // Tampilan untuk notifikasi (TODO)
        }
        composable(Screen.Account.route) {
            // Tampilan untuk akun (TODO)
        }
    }
}
