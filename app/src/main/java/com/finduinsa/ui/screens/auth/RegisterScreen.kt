package com.finduinsa.ui.screens.auth

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.* // Menggunakan Material3
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource // Untuk ic_camera_placeholder
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.finduinsa.R // Pastikan R diimpor
import com.finduinsa.navigation.Screen
import com.finduinsa.presentation.auth.RegisterViewModel
import com.finduinsa.ui.theme.FindUINSATheme
import com.finduinsa.ui.theme.LightGrayBackground // Warna kustom (bisa tidak digunakan lagi untuk field bg)
import com.finduinsa.ui.theme.PlaceholderGray // Warna kustom
import com.finduinsa.ui.theme.TealBlue // Warna kustom


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel() // Injeksi RegisterViewModel
) {
    val context = LocalContext.current

    // Mengamati state dari ViewModel
    val nim by viewModel.nim.collectAsState()
    val password by viewModel.password.collectAsState()
    val fullName by viewModel.fullName.collectAsState()
    val studyProgram by viewModel.studyProgram.collectAsState()
    val faculty by viewModel.faculty.collectAsState()
    val phoneNumber by viewModel.phoneNumber.collectAsState()
    val ktmUri by viewModel.ktmUri.collectAsState()
    val isRegistered by viewModel.isRegistered.collectAsState()
    val isRegisterButtonEnabled by viewModel.isRegisterButtonEnabled.collectAsState() // Amati status tombol

    var studyProgramExpanded by remember { mutableStateOf(false) }
    var facultyExpanded by remember { mutableStateOf(false) }

    // Data untuk dropdown Fakultas dan Program Studi
    val fakultasToProdi = remember {
        mapOf(
            "Adab dan Humaniora" to listOf("Bahasa dan Sastra Arab", "Sejarah Peradaban Islam", "Sastra Inggris", "Sastra Indonesia"),
            "Dakwah dan Komunikasi" to listOf("Bimbingan dan Konseling Islam", "Manajemen Dakwah", "Pengembangan Masyarakat Islam", "Komunikasi dan Penyiaran Islam", "Ilmu Komunikasi"),
            "Psikologi dan Kesehatan" to listOf("Psikologi", "Gizi"),
            "Sains dan Teknologi" to listOf("Matematika", "Biologi", "Ilmu Kelautan", "Sistem Informasi", "Arsitektur", "Teknik Lingkungan", "Teknik Sipil"),
            "Sosial dan Ilmu Politik" to listOf("Sosiologi", "Ilmu Politik", "Hubungan Internasional"),
            "Syariah dan Hukum" to listOf("Hukum Ekonomi Syariah", "Ilmu Falak", "Hukum Pidana Islam", "Hukum Tata Negara", "Hukum Keluarga Islam", "Hukum", "Perbandingan Mazhab"),
            "Tarbiyah dan Keguruan" to listOf("PGMI", "PIAUD", "Manajemen Pendidikan Islam", "Pendidikan Agama Islam", "Pendidikan Bahasa Arab", "Pendidikan Matematika", "Pendidikan IPA", "Pendidikan Bahasa Inggris", "Pendidikan Profesi Guru"),
            "Ekonomi dan Bisnis Islam" to listOf("Akuntansi", "Ekonomi Syariah", "Ilmu Ekonomi", "Manajemen", "Manajemen Zakat dan Wakaf")
        )
    }
    val fakultasOptions = remember { fakultasToProdi.keys.toList() }
    val prodiOptions by remember(faculty) {
        derivedStateOf { fakultasToProdi[faculty] ?: emptyList() }
    }

    // Launcher untuk memilih gambar KTM
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.onKtmUriChange(uri)
    }

    // LaunchedEffect untuk navigasi setelah registrasi berhasil
    LaunchedEffect(key1 = isRegistered) {
        if (isRegistered) {
            Toast.makeText(context, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Register.route) { inclusive = true }
            }
            viewModel.onRegisterSuccessHandled()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Daftar",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6B7280), // Warna teks sesuai desain
                        fontSize = 16.sp // Ukuran font sesuai desain
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFF6B7280) // Warna ikon sesuai desain
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White, // Background putih
                    titleContentColor = Color(0xFF6B7280) // Mengikuti warna title
                ),
                scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior() // Menghilangkan elevation
            )
        },
        containerColor = Color.White // Background putih untuk seluruh layar
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()), // Aktifkan scroll
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp)) // Jarak dari top bar

            // Nomor Induk Mahasiswa (NIM)
            FieldWithLabel(label = "Nomor Induk Mahasiswa") {
                CustomOutlinedTextField(
                    value = nim,
                    onValueChange = viewModel::onNimChange,
                    placeholder = "Contoh: 12345678",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Kata Sandi
            FieldWithLabel(label = "Kata Sandi") {
                CustomOutlinedTextField(
                    value = password,
                    onValueChange = viewModel::onPasswordChange,
                    placeholder = "Masukkan kata sandi",
                    visualTransformation = PasswordVisualTransformation()
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Nama Lengkap
            FieldWithLabel(label = "Nama Lengkap") {
                CustomOutlinedTextField(
                    value = fullName,
                    onValueChange = viewModel::onFullNameChange,
                    placeholder = "Nama lengkap Anda"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Program Studi Dropdown
            FieldWithLabel(label = "Program Studi") {
                Box {
                    CustomOutlinedTextField(
                        value = studyProgram,
                        onValueChange = {}, // ReadOnly
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Dropdown Arrow",
                                modifier = Modifier.clickable { studyProgramExpanded = true }
                            )
                        },
                        modifier = Modifier.clickable { studyProgramExpanded = true }
                    )
                    DropdownMenu(
                        expanded = studyProgramExpanded,
                        onDismissRequest = { studyProgramExpanded = false },
                        // Modifier untuk DropdownMenu harus diatur agar tidak terlalu lebar
                        // Menggunakan .fillMaxWidth() atau .width(IntrinsicSize.Max)
                        // Note: fillMaxWidth() pada DropdownMenu di Box dapat menyebabkan issue layout
                        // Ganti dengan Modifier.width(with(LocalDensity.current) { 312.dp }) jika lebar dropdown tidak pas
                        modifier = Modifier.fillMaxWidth() // Sesuaikan lebar dropdown
                    ) {
                        prodiOptions.forEach { program -> // Menggunakan prodiOptions yang dinamis
                            DropdownMenuItem(
                                text = { Text(program) },
                                onClick = {
                                    viewModel.onStudyProgramChange(program)
                                    studyProgramExpanded = false
                                }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Fakultas Dropdown
            FieldWithLabel(label = "Fakultas") {
                Box {
                    CustomOutlinedTextField(
                        value = faculty,
                        onValueChange = {}, // ReadOnly
                        readOnly = true,
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Dropdown Arrow",
                                modifier = Modifier.clickable { facultyExpanded = true }
                            )
                        },
                        modifier = Modifier.clickable { facultyExpanded = true }
                    )
                    DropdownMenu(
                        expanded = facultyExpanded,
                        onDismissRequest = { facultyExpanded = false },
                        modifier = Modifier.fillMaxWidth() // Sesuaikan lebar dropdown
                    ) {
                        fakultasOptions.forEach { fklts ->
                            DropdownMenuItem(
                                text = { Text(fklts) },
                                onClick = {
                                    viewModel.onFacultyChange(fklts)
                                    // Reset program studi ketika fakultas berubah
                                    viewModel.onStudyProgramChange(fakultasToProdi[fklts]?.firstOrNull() ?: "Pilih Program Studi")
                                    facultyExpanded = false
                                }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Nomor Handphone
            FieldWithLabel(label = "Nomor Handphone") {
                CustomOutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { newText ->
                        // Pastikan prefix +62 tetap ada
                        if (!newText.startsWith("+62")) {
                            viewModel.onPhoneNumberChange("+62" + newText.removePrefix("+62"))
                        } else {
                            viewModel.onPhoneNumberChange(newText)
                        }
                    },
                    // Placeholder hanya untuk bagian setelah +62
                    placeholder = "812...",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Upload KTM
            FieldWithLabel(label = "Upload KTM") {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp) // Tinggi disesuaikan agar sesuai desain
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White) // Background putih
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)) // Border tipis
                        .clickable { pickImageLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (ktmUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(ktmUri),
                            contentDescription = "Selected KTM",
                            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Button(
                            onClick = { pickImageLauncher.launch("image/*") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9E9E9E)), // Warna abu-abu gelap
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Upload Foto", color = Color.White)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))

            // Tombol Daftar
            Button(
                onClick = {
                    viewModel.register()
                },
                enabled = isRegisterButtonEnabled, // Kontrol status aktif tombol
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TealBlue)
            ) {
                Text(text = "Daftar", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(24.dp)) // Jarak dari bawah
        }
    }
}

/**
 * Composable helper untuk menampilkan label di atas OutlinedTextField atau komponen lainnya.
 */
@Composable
fun FieldWithLabel(
    label: String,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color(0xFF6B7280), // Warna teks label sesuai desain
            modifier = Modifier.padding(bottom = 4.dp)
        )
        content()
    }
}

/**
 * Composable kustom untuk OutlinedTextField dengan gaya desain yang konsisten.
 * Tidak memiliki label bawaan karena label ditangani oleh FieldWithLabel.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    placeholder: String = "",
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: androidx.compose.ui.text.input.VisualTransformation = androidx.compose.ui.text.input.VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = PlaceholderGray) },
        modifier = modifier.height(56.dp), // Tinggi tetap agar konsisten
        readOnly = readOnly,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.colors( // Menggunakan TextFieldDefaults.colors
            focusedIndicatorColor = TealBlue,
            unfocusedIndicatorColor = Color.LightGray, // Border warna LightGray
            focusedContainerColor = Color.White, // Background putih
            unfocusedContainerColor = Color.White, // Background putih
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            cursorColor = TealBlue,
            disabledIndicatorColor = Color.LightGray, // Tambahkan untuk disabled
            disabledContainerColor = Color.White, // Tambahkan untuk disabled
            disabledTextColor = Color.Black.copy(alpha = 0.38f) // Tambahkan untuk disabled
        ),
        singleLine = true
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun RegisterScreenPreview() {
    FindUINSATheme {
        RegisterScreen(navController = rememberNavController())
    }
}