package com.finduinsa.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object LostReport : Screen("lost_report")
    object FoundReport : Screen("found_report")
    object Messages : Screen("messages")
    object Notifications : Screen("notifications")
    object Account : Screen("account")
}