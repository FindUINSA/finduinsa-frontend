package com.finduinsa.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.finduinsa.navigation.Screen
import com.finduinsa.presentation.auth.LoginViewModel
import com.finduinsa.ui.theme.FindUINSATheme
import com.finduinsa.ui.theme.LightGrayBackground
import com.finduinsa.ui.theme.PlaceholderGray
import com.finduinsa.ui.theme.TealBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val nim by viewModel.nim.collectAsState()
    val password by viewModel.password.collectAsState()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val loginError by viewModel.loginError.collectAsState() // Amati pesan error
    val isLoginButtonEnabled by viewModel.isLoginButtonEnabled.collectAsState() // Amati status tombol

    LaunchedEffect(key1 = isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
            viewModel.onLoginSuccessHandled()
        }
    }

    // Tampilkan Toast jika ada pesan error
    LaunchedEffect(key1 = loginError) {
        loginError?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.onLoginErrorShown() // Reset error setelah ditampilkan
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Selamat Datang",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // NIM TextField
        OutlinedTextField(
            value = nim,
            onValueChange = viewModel::onNimChange,
            label = { Text("NIM") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = TealBlue,
                unfocusedIndicatorColor = Color.LightGray, // Border warna LightGray
                focusedContainerColor = Color.White, // Background putih
                unfocusedContainerColor = Color.White, // Background putih
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = TealBlue
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Kata Sandi TextField
        OutlinedTextField(
            value = password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Kata Sandi") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = TealBlue,
                unfocusedIndicatorColor = Color.LightGray, // Border warna LightGray
                focusedContainerColor = Color.White, // Background putih
                unfocusedContainerColor = Color.White, // Background putih
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                cursorColor = TealBlue
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Lupa kata sandi?
        TextButton(
            onClick = { /* TODO: Navigasi ke lupa kata sandi */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(
                text = "Lupa kata sandi?",
                color = TealBlue,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Tombol Masuk
        Button(
            onClick = { viewModel.login() },
            enabled = isLoginButtonEnabled, // Kontrol status aktif tombol
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = TealBlue)
        ) {
            Text(text = "Masuk", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Teks "Belum punya akun? Daftar"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Belum punya akun FindUINSA? ",
                color = Color.Gray,
                fontSize = 14.sp
            )
            TextButton(
                onClick = { navController.navigate(Screen.Register.route) },
                modifier = Modifier.padding(horizontal = 0.dp)
            ) {
                Text(
                    text = "Daftar",
                    color = TealBlue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun LoginScreenPreview() {
    FindUINSATheme {
        LoginScreen(navController = rememberNavController())
    }
}