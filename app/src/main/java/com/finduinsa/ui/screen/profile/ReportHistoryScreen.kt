package com.finduinsa.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.finduinsa.BottomNavigationBar

// ✅ Tambahkan di bagian atas seperti ini
data class Report(
    val id: Int,
    val title: String,
    val date: String,
    val type: String // "Kehilangan" atau "Penemuan"
)

@Composable
fun ReportHistoryScreen(navController: NavHostController) {
    val reports = listOf(
        Report(1, "Kehilangan Dompet", "10 Mei 2025", "Kehilangan"),
        Report(2, "Menemukan Kunci", "5 Mei 2025", "Penemuan")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Riwayat Laporan", color = Color.White) },
                backgroundColor = Color(0xFF0288D1),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            if (reports.isEmpty()) {
                Text("Belum ada laporan.")
            } else {
                reports.forEach { report ->
                    val indicatorColor = if (report.type == "Kehilangan") Color.Red else Color.Green

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable {
                                navController.navigate("report_detail/${report.id}")
                            },
                        elevation = 4.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(report.title, style = MaterialTheme.typography.h6)
                                Text(report.date, style = MaterialTheme.typography.body2, color = Color.Gray)
                            }

                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .padding(start = 8.dp)
                                    .background(color = indicatorColor, shape = MaterialTheme.shapes.small)
                            )
                        }
                    }
                }
            }
        }
    }
}
