package com.finduinsa.ui.screens.LostReport


import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.finduinsa.data.model.PostType
import com.finduinsa.presentatio.LostReport.ReportViewModel
import com.finduinsa.presentation.lostreport.ReportForm
import com.finduinsa.ui.theme.FindUINSATheme
import com.finduinsa.ui.theme.LightBlueBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LostReportScreen(
    navController: NavController,
    viewModel: ReportViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Laporan Kehilangan") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LightBlueBackground,
                    titleContentColor = Color.Black
                )
            )
        },
        containerColor = LightBlueBackground
    ) { paddingValues ->
        ReportForm(
            modifier = Modifier.padding(paddingValues),
            reportType = "Kehilangan",
            onSubmit = { itemName, itemDescription, itemCategory, date, location, imageUrl -> // Tambahkan imageUrl
                viewModel.submitReport(itemName, itemDescription, itemCategory, date, location, PostType.LOST, imageUrl)
            },
            onNavigateBack = { navController.popBackStack() }
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 700)
@Composable
fun LostReportScreenPreview() {
    FindUINSATheme {
        LostReportScreen(navController = rememberNavController())
    }
}