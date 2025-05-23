package com.finduinsa.ui.screen.auth

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.finduinsa.ui.screen.auth.LabelledTextField

@Composable
fun RegisterScreen(navController: NavHostController) {
    var nim by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("+62") }

    // Upload KTM
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri.value = uri
    }

    // Relasi Fakultas dan Prodi
    val fakultasToProdi = mapOf(
        "Adab dan Humaniora" to listOf("Bahasa dan Sastra Arab", "Sejarah Peradaban Islam", "Sastra Inggris", "Sastra Indonesia"),
        "Dakwah dan Komunikasi" to listOf("Bimbingan dan Konseling Islam", "Manajemen Dakwah", "Pengembangan Masyarakat Islam", "Komunikasi dan Penyiaran Islam", "Ilmu Komunikasi"),
        "Psikologi dan Kesehatan" to listOf("Psikologi", "Gizi"),
        "Sains dan Teknologi" to listOf("Matematika", "Biologi", "Ilmu Kelautan", "Sistem Informasi", "Arsitektur", "Teknik Lingkungan", "Teknik Sipil"),
        "Sosial dan Ilmu Politik" to listOf("Sosiologi", "Ilmu Politik", "Hubungan Internasional"),
        "Syariah dan Hukum" to listOf("Hukum Ekonomi Syariah", "Ilmu Falak", "Hukum Pidana Islam", "Hukum Tata Negara", "Hukum Keluarga Islam", "Hukum", "Perbandingan Mazhab"),
        "Tarbiyah dan Keguruan" to listOf("PGMI", "PIAUD", "Manajemen Pendidikan Islam", "Pendidikan Agama Islam", "Pendidikan Bahasa Arab", "Pendidikan Matematika", "Pendidikan IPA", "Pendidikan Bahasa Inggris", "Pendidikan Profesi Guru"),
        "Ekonomi dan Bisnis Islam" to listOf("Akuntansi", "Ekonomi Syariah", "Ilmu Ekonomi", "Manajemen", "Manajemen Zakat dan Wakaf")

    )
    val fakultasOptions = fakultasToProdi.keys.toList()
    var selectedFakultas by remember { mutableStateOf(fakultasOptions[0]) }

    val prodiOptions by derivedStateOf { fakultasToProdi[selectedFakultas] ?: emptyList() }
    var selectedProdi by remember { mutableStateOf(prodiOptions.firstOrNull() ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Daftar", color = Color(0xFF6B7280), fontSize = 16.sp)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color(0xFF6B7280))
                    }
                },
                backgroundColor = Color.White,
                elevation = 0.dp
            )
        },
        backgroundColor = Color.White,
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LabelledTextField(
                        label = "Nomor Induk Mahasiswa",
                        value = nim,
                        onValueChange = { nim = it },
                        keyboardType = KeyboardType.Number
                    )
                    LabelledTextField(
                        label = "Kata Sandi",
                        value = password,
                        onValueChange = { password = it },
                        keyboardType = KeyboardType.Password,
                        isPassword = true
                    )
                    LabelledTextField(
                        label = "Nama",
                        value = name,
                        onValueChange = { name = it },
                        keyboardType = KeyboardType.Text
                    )
                    LabelledTextField(
                        label = "Nomor Handphone",
                        value = phone,
                        onValueChange = { phone = it },
                        keyboardType = KeyboardType.Phone
                    )


                    // Dropdown Fakultas
                    DropdownOutlined(
                        label = "Fakultas",
                        options = fakultasOptions,
                        selectedOption = selectedFakultas,
                        onOptionSelected = {
                            selectedFakultas = it
                            selectedProdi = fakultasToProdi[it]?.firstOrNull() ?: ""
                        }
                    )

                    // Dropdown Program Studi
                    DropdownOutlined(
                        label = "Program Studi",
                        options = prodiOptions,
                        selectedOption = selectedProdi,
                        onOptionSelected = { selectedProdi = it }
                    )

                    // Upload KTM
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFFE0F7FA))
                                .align(Alignment.CenterHorizontally)
                                .clickable { launcher.launch("image/*") },
                            contentAlignment = Alignment.Center
                        ) {
                            if (imageUri.value != null) {
                                Image(
                                    painter = rememberAsyncImagePainter(imageUri.value),
                                    contentDescription = "KTM",
                                    modifier = Modifier.fillMaxSize()
                                )
                            } else {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = Icons.Default.CameraAlt,
                                        contentDescription = "Upload KTM",
                                        tint = Color(0xFF0288D1),
                                        modifier = Modifier.size(48.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("Upload KTM", color = Color(0xFF0288D1))
                                }
                            }
                        }
                    }

                    // Tombol Daftar
                    Button(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0288D1))
                    ) {
                        Text(
                            text = "Daftar",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun DropdownOutlined(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.White
            )
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    onOptionSelected(option)
                    expanded = false
                }) {
                    Text(option)
                }
            }
        }
    }
}
