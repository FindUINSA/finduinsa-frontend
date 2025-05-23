package com.finduinsa.ui.screen.mainmenu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.finduinsa.BottomNavigationBar  // pastikan path ini benar

@Composable
fun MainMenuScreen(navController: NavHostController, userName: String = "Dinda") {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->
        // Konten utama di sini, kasih padding dari Scaffold supaya gak ketutup bottom bar
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFE0F7FA))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,  // Vertikal tengah
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hubungkan, Temukan, Kembalikan!",
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(28.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        TextField(
                            value = "",
                            onValueChange = {},
                            placeholder = { Text("Apa yang Anda cari, $userName?") },
                            trailingIcon = {
                                Icon(Icons.Default.Search, contentDescription = "Search")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color(0xFFF2F2F2),
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { navController.navigate("lost_report") }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Laporan Kehilangan",
                                tint = Color.Red,
                                modifier = Modifier.size(32.dp)
                            )
                            Text("Laporan Kehilangan", color = Color.Red, fontSize = 12.sp)
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .weight(1f)
                                .clickable { navController.navigate("found_report") }
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Laporan Penemuan",
                                tint = Color.Green,
                                modifier = Modifier.size(32.dp)
                            )
                            Text("Laporan Penemuan", color = Color.Green, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}


