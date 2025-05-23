package com.finduinsa.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.finduinsa.BottomNavigationBar
import com.finduinsa.R
import com.finduinsa.ui.screen.navigation.Screen

@Composable
fun ProfileScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profil", color = Color.White) },
                backgroundColor = Color(0xFF0288D1),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("main_menu") {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }){
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Foto profil dummy (letakkan avatar.png di drawable)
            Image(
                painter = painterResource(id = R.drawable.profile), // ganti dengan foto asli kalau ada
                contentDescription = "Foto Profil",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ProfileRow(label = "Nama", value = "Muhammad Hilal")
                    ProfileRow(label = "NIM", value = "1234567890")
                    ProfileRow(label = "Program Studi", value = "Sistem Informasi")
                    ProfileRow(label = "Fakultas", value = "Sains dan Teknologi")
                    ProfileRow(label = "No. HP", value = "+62 81234567890")

                    Spacer(modifier = Modifier.height(24.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("report_history") // ganti dengan screen tujuan
                            },
                        elevation = 4.dp
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.List, // ikon daftar laporan
                                contentDescription = "Riwayat Laporan",
                                tint = Color(0xFF0288D1)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text("Riwayat Laporan Saya", fontSize = 16.sp)
                        }
                    }

                }
            }

            Spacer(modifier = Modifier.height(32.dp))


            Button(
                onClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text("Logout", color = Color.White)
            }
        }
    }
}

@Composable
fun ProfileRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 16.sp, color = Color.Gray)
        Text(value, fontSize = 16.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
    }
}