package com.finduinsa.ui.screen.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object MainMenu : Screen("main_menu")
    object Messages : Screen("messages")
    object Notif : Screen("notif")
    object Profile : Screen("profile")
    object LostReport : Screen("lost_report")
    object FoundReport : Screen("found_report")
}
