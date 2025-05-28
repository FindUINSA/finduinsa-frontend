package com.finduinsa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState // Import ini
import androidx.navigation.compose.rememberNavController
import com.finduinsa.navigation.Screen
import com.finduinsa.ui.theme.FindUINSATheme


@Composable
fun AppBottomNavigationBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(
            icon = Icons.Default.Home,
            label = "Home",
            isSelected = currentRoute == Screen.Home.route,
            onClick = { navController.navigate(Screen.Home.route) {
                popUpTo(navController.graph.startDestinationId) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }}
        )
        BottomNavItem(
            icon = Icons.Default.Person, // Ganti dengan ikon "Pesan" yang sesuai jika ada
            label = "Pesan",
            isSelected = currentRoute == Screen.Messages.route,
            onClick = { navController.navigate(Screen.Messages.route) {
                popUpTo(navController.graph.startDestinationId) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }}
        )
        BottomNavItem(
            icon = Icons.Default.Notifications,
            label = "Notifikasi",
            isSelected = currentRoute == Screen.Notifications.route,
            onClick = { navController.navigate(Screen.Notifications.route) {
                popUpTo(navController.graph.startDestinationId) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }}
        )
        BottomNavItem(
            icon = Icons.Default.AccountCircle,
            label = "Akun",
            isSelected = currentRoute == Screen.Account.route,
            onClick = { navController.navigate(Screen.Account.route) {
                popUpTo(navController.graph.startDestinationId) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }}
        )
    }
}
// BottomNavItem Composable tetap sama
@Composable
fun BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            fontSize = 10.sp,
            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
        )
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun AppBottomNavigationBarPreview() {
    FindUINSATheme {
        AppBottomNavigationBar(navController = rememberNavController())
    }
}