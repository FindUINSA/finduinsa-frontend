// app/src/main/java/com/finduinsa/presentation/lostreport/ReportForm.kt
package com.finduinsa.presentation.lostreport

import android.app.DatePickerDialog
import android.net.Uri
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.finduinsa.R
import com.finduinsa.ui.theme.FindUINSATheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportForm(
    modifier: Modifier = Modifier,
    reportType: String, // "Kehilangan" atau "Penemuan"
    onSubmit: (itemName: String, itemDescription: String, itemCategory: String, date: String, location: String, imageUrl: String?) -> Unit,
    onNavigateBack: () -> Unit // Callback untuk navigasi kembali ke home
) {
    val context = LocalContext.current

    var itemName by remember { mutableStateOf("") }
    var itemNameError by remember { mutableStateOf(false) }

    var itemDescription by remember { mutableStateOf("") }
    var itemDescriptionError by remember { mutableStateOf(false) }

    var itemCategory by remember { mutableStateOf("Pilih Kategori") }
    var itemCategoryError by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    var date by remember { mutableStateOf("") }
    var dateError by remember { mutableStateOf(false) }

    var location by remember { mutableStateOf("") }
    var locationError by remember { mutableStateOf(false) }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val categories = listOf("Elektronik", "Non Elektronik")

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(selectedYear, selectedMonth, selectedDayOfMonth)
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            date = sdf.format(selectedCalendar.time)
            dateError = false
        }, year, month, day
    )


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ... (Upload Photo Section tetap sama)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .border(2.dp, Color.LightGray, RoundedCornerShape(12.dp))
                .clickable { pickImageLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(selectedImageUri),
                    contentDescription = "Selected Photo",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_addimg),
                    contentDescription = "Upload Photo",
                    modifier = Modifier.size(64.dp),
                    alignment = Alignment.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Form Fields
        CustomTextField(
            value = itemName,
            onValueChange = {
                itemName = it
                itemNameError = it.isBlank()
            },
            label = "Nama Barang",
            placeholderText = "contoh HP Redmi Note 8", // <-- Ditambahkan di sini
            isError = itemNameError,
            errorMessage = "Nama barang wajib diisi"
        )
        Spacer(modifier = Modifier.height(12.dp))
        CustomTextField(
            value = itemDescription,
            onValueChange = {
                itemDescription = it
                itemDescriptionError = it.isBlank()
            },
            label = "Deskripsi Barang",
            placeholderText = "jenis, warna, gantungan, dll", // <-- Ditambahkan di sini
            singleLine = false,
            minLines = 3,
            isError = itemDescriptionError,
            errorMessage = "Deskripsi barang wajib diisi"
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Kategori Barang Dropdown (tidak ada placeholder)
        Box {
            CustomTextField(
                value = itemCategory,
                onValueChange = {},
                label = "Kategori Barang",
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Dropdown Arrow",
                        Modifier.clickable { expanded = true }
                    )
                },
                modifier = Modifier.clickable { expanded = true },
                isError = itemCategoryError,
                errorMessage = "Kategori wajib diisi"
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            itemCategory = category
                            itemCategoryError = false
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))

        // Tanggal Kehilangan/Penemuan (tidak ada placeholder)
        CustomTextField(
            value = date,
            onValueChange = { /* date tidak diubah langsung dari input */ },
            label = "Tanggal $reportType",
            readOnly = true,
            trailingIcon = {
                Icon(
                    painter = painterResource(id=R.drawable.ic_calendar),
                    contentDescription = "Pilih Tanggal",
                    modifier = Modifier.clickable { datePickerDialog.show() }
                )
            },
            modifier = Modifier.clickable { datePickerDialog.show() },
            isError = dateError,
            errorMessage = "Tanggal wajib diisi"
        )
        Spacer(modifier = Modifier.height(12.dp))

        // Lokasi Kehilangan/Penemuan
        CustomTextField(
            value = location,
            onValueChange = {
                location = it
                locationError = it.isBlank()
            },
            label = "Lokasi $reportType",
            placeholderText = "gedung saintek, kampus 2", // <-- Ditambahkan di sini
            isError = locationError,
            errorMessage = "Lokasi wajib diisi"
        )
        Spacer(modifier = Modifier.height(24.dp))

        val isFormValid = itemName.isNotBlank() &&
                itemDescription.isNotBlank() &&
                itemCategory != "Pilih Kategori" &&
                date.isNotBlank() &&
                location.isNotBlank()

        Button(
            onClick = {
                itemNameError = itemName.isBlank()
                itemDescriptionError = itemDescription.isBlank()
                itemCategoryError = itemCategory == "Pilih Kategori"
                dateError = date.isBlank()
                locationError = location.isBlank()

                if (isFormValid) {
                    onSubmit(itemName, itemDescription, itemCategory, date, location, selectedImageUri?.toString())
                    Toast.makeText(context, "Laporan berhasil dibuat!", Toast.LENGTH_SHORT).show()
                    onNavigateBack()
                } else {
                    Toast.makeText(context, "Mohon lengkapi semua kolom yang wajib diisi.", Toast.LENGTH_SHORT).show()
                }
            },
            enabled = isFormValid,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "TAMBAHKAN", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    readOnly: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    minLines: Int = 1,
    isError: Boolean = false,
    errorMessage: String = "",
    placeholderText: String? = null // <-- Parameter baru untuk placeholder
) {
    Column(modifier = modifier) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = if (placeholderText != null) { { Text(placeholderText) } } else null, // <-- Digunakan di sini
            readOnly = readOnly,
            trailingIcon = trailingIcon,
            singleLine = singleLine,
            minLines = minLines,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                errorContainerColor = Color.White,
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                errorLabelColor = MaterialTheme.colorScheme.error,
                errorSupportingTextColor = MaterialTheme.colorScheme.error
            ),
            isError = isError
        )
        if (isError) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

// ... (Preview functions tetap sama)
@Preview(showBackground = true, widthDp = 360, heightDp = 700)
@Composable
fun ReportFormPreview() {
    FindUINSATheme {
        // Untuk preview, kita bisa memberikan lambda kosong atau dummy
        ReportForm(reportType = "Kehilangan", onSubmit = { _, _, _, _, _, _ -> }, onNavigateBack = {})
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 700)
@Composable
fun ReportFormFoundPreview() {
    FindUINSATheme {
        ReportForm(reportType = "Penemuan", onSubmit = { _, _, _, _, _, _ -> }, onNavigateBack = {})
    }
}