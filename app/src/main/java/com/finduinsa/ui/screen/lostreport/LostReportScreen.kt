package com.finduinsa.ui.screen.lostreport

import android.app.DatePickerDialog
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import java.util.*

@Composable
fun LostReportScreen(navController: NavHostController) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var itemName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var dateLost by remember { mutableStateOf("") }

    var expandedKategori by remember { mutableStateOf(false) }
    val kategoriOptions = listOf("Elektronik", "Non Elektronik")
    var selectedKategori by remember { mutableStateOf("") }

    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri.value = uri
    }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val m = month + 1
            dateLost = "%04d-%02d-%02d".format(year, m, dayOfMonth)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    var showSuccessDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Laporan Kehilangan") },
                backgroundColor = Color(0xFF0288D1),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFE0F7FA))
                        .clickable { launcher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri.value != null) {
                        Image(
                            painter = rememberAsyncImagePainter(imageUri.value),
                            contentDescription = "Foto Barang",
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "Upload Foto",
                                tint = Color(0xFF0288D1),
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Upload Foto",
                                color = Color(0xFF0288D1),
                                style = MaterialTheme.typography.subtitle1
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("Nama Barang", style = MaterialTheme.typography.subtitle1)
                    OutlinedTextField(
                        value = itemName,
                        onValueChange = { itemName = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White),
                        placeholder = { Text("Masukkan nama barang yang hilang") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Deskripsi", style = MaterialTheme.typography.subtitle1)
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(Color.White),
                        placeholder = { Text("Deskripsikan barang hilang secara detail") },
                        maxLines = 4,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Lokasi Kehilangan", style = MaterialTheme.typography.subtitle1)
                    OutlinedTextField(
                        value = location,
                        onValueChange = { location = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White),
                        placeholder = { Text("Tempat barang hilang") },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Tanggal Kehilangan", style = MaterialTheme.typography.subtitle1)
                    OutlinedTextField(
                        value = dateLost,
                        onValueChange = {},
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { datePickerDialog.show() },
                        readOnly = true,
                        placeholder = { Text("Pilih tanggal") },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = "Pilih Tanggal",
                                modifier = Modifier.clickable { datePickerDialog.show() }
                            )
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            backgroundColor = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Kategori Barang", style = MaterialTheme.typography.subtitle1)
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.TopStart)) {

                        OutlinedTextField(
                            value = selectedKategori,
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Pilih Kategori") },
                            trailingIcon = {
                                IconButton(onClick = { expandedKategori = !expandedKategori }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "Dropdown"
                                    )
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { expandedKategori = true },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                backgroundColor = Color.White
                            )
                        )

                        DropdownMenu(
                            expanded = expandedKategori,
                            onDismissRequest = { expandedKategori = false },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            kategoriOptions.forEach { option ->
                                DropdownMenuItem(onClick = {
                                    selectedKategori = option
                                    expandedKategori = false
                                }) {
                                    Text(option)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            // Tampilkan dialog sukses
                            showSuccessDialog = true
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .height(48.dp)
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF0288D1))
                    ) {
                        Text(
                            text = "Kirim Laporan",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        )
                    }
                }
            }
        }
    )

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Success",
                        tint = Color(0xFF4CAF50),
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Laporan Terkirim")
                }
            },
            text = {
                Text("Laporan kehilangan Anda telah berhasil dikirim.")
            },
            confirmButton = {
                TextButton(onClick = {
                    showSuccessDialog = false
                    navController.navigate("main_menu") {
                        popUpTo("lost_report") { inclusive = true }
                    }
                }) {
                    Text("OK")
                }
            },
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Color.White
        )
    }
}
