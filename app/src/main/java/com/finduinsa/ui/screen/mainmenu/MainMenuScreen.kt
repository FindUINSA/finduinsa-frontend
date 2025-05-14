package com.finduinsa.ui.screen.mainmenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainMenuScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Selamat Datang di FindUINSA", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { /* navigasi ke Postingan Barang Hilang */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Postingan Barang Hilang")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { /* navigasi ke Postingan Barang Ditemukan */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Postingan Barang Ditemukan")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { /* navigasi ke Chat Admin */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Chat Admin")
        }
    }
}
