package com.finduinsa.ui.screen.profile

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.finduinsa.BottomNavigationBar

@Composable
fun ReportDetailScreen(navController: NavHostController, reportId: Int) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Laporan") },
                backgroundColor = Color(0xFF0288D1),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Text("Laporan ID: $reportId", style = MaterialTheme.typography.h6)
            Button(onClick = { /* TODO: Edit */ }, modifier = Modifier.padding(top = 8.dp)) {
                Text("Edit")
            }
            Button(onClick = { /* TODO: Hapus */ }, modifier = Modifier.padding(top = 8.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)) {
                Text("Hapus", color = Color.White)
            }
        }
    }
}
