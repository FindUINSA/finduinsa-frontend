package com.finduinsa

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.finduinsa.ui.screen.auth.LoginScreen
import com.finduinsa.ui.screen.auth.RegisterScreen
import com.finduinsa.ui.screen.foundreport.FoundReportScreen
import com.finduinsa.ui.screen.lostreport.LostReportScreen
import com.finduinsa.ui.screen.mainmenu.*
import com.finduinsa.ui.screen.navigation.Screen
import com.finduinsa.ui.screen.chat.ChatListScreen
import com.finduinsa.ui.screen.chat.ChatDetailScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.finduinsa.ui.screen.notif.NotifScreen
import com.finduinsa.ui.screen.profile.ProfileScreen
import com.finduinsa.ui.screen.profile.ReportHistoryScreen
import com.finduinsa.ui.screen.profile.ReportDetailScreen

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
            MainMenuScreen(navController)
        }
        composable("lost_report") {
            LostReportScreen(navController)
        }
        composable("found_report") {
            FoundReportScreen(navController)
        }

        composable("messages") {
            ChatListScreen(navController)
        }
        composable(
            route = "chat_detail/{userName}",
            arguments = listOf(navArgument("userName") { type = NavType.StringType })
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: ""
            ChatDetailScreen(userName, navController)
        }

        composable("notif") {
            NotifScreen(navController)
        }

        composable("profile") {
            ProfileScreen(navController)
        }
        composable("report_history") {
            ReportHistoryScreen(navController) // buat screen ini
        }
        composable("report_detail/{reportId}") { backStackEntry ->
            val reportId = backStackEntry.arguments?.getString("reportId")?.toIntOrNull()
            reportId?.let {
                ReportDetailScreen(navController, it)
            }
        }


    }
}
