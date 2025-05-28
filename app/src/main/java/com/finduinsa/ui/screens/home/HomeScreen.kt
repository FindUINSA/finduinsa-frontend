package com.finduinsa.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
// Hapus atau tambahkan impor ini jika Anda menggunakan Add icon di tempat lain:
// import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.finduinsa.R
import com.finduinsa.data.model.Post
import com.finduinsa.data.model.PostType
import com.finduinsa.navigation.Screen
import com.finduinsa.ui.theme.FindUINSATheme
import com.finduinsa.ui.theme.GreenButton
import com.finduinsa.ui.theme.LightBlueBackground
import com.finduinsa.ui.theme.RedButton
import com.finduinsa.ui.theme.GrayText

// Import komponen AppBottomNavigationBar yang sudah dipisah
import com.finduinsa.ui.components.AppBottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            TopHomeSection(
                searchQuery = searchQuery,
                onSearchQueryChanged = viewModel::onSearchQueryChanged, // Ini harus mengacu ke fungsi di ViewModel
                onLostReportClick = { navController.navigate(Screen.LostReport.route) },
                onFoundReportClick = { navController.navigate(Screen.FoundReport.route) }
            )
        },
        bottomBar = { AppBottomNavigationBar(navController) }, // Menggunakan komponen yang sudah dipisah
        containerColor = LightBlueBackground
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.error != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Error: ${state.error}", color = MaterialTheme.colorScheme.error)
                }
            } else if (state.posts.isEmpty()) {
                EmptyPostings()
            } else {
                PostingsList(posts = state.posts)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopHomeSection(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit, // Parameter ini harus ada di TopHomeSection
    onLostReportClick: () -> Unit,
    onFoundReportClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp), // Beri sedikit padding bawah agar tidak terlalu mepet dengan list
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp), // Bentuk melengkung di bawah
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Hubungkan, Temukan,",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Kembalikan!",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_profile_dummy),
                    contentDescription = "User Profile",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable { /* TODO: Navigate to profile upload/edit */ }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = searchQuery,
                onValueChange = onSearchQueryChanged, // Menggunakan parameter onSearchQueryChanged
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Apa yang Anda cari, Dinda?", color = GrayText) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = GrayText) },
                shape = RoundedCornerShape(24.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF0F0F0),
                    unfocusedContainerColor = Color(0xFFF0F0F0),
                    disabledContainerColor = Color(0xFFF0F0F0),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = RedButton),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .clickable { onLostReportClick() } // Navigasi ke laporan kehilangan
                ) {
                    Row(
                        modifier = Modifier
                            .padding(vertical = 12.dp, horizontal = 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_lost_report), // Icon baru untuk laporan
                            contentDescription = "Laporan Kehilangan",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Laporan\nKehilangan",
                            fontSize = 14.sp,
                            color = Color.White,
                            lineHeight = 16.sp // Menjaga dua baris tetap dalam satu ukuran
                        )
                    }
                }

                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = GreenButton),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(4.dp)
                        .clickable { onFoundReportClick() } // Navigasi ke laporan penemuan
                ) {
                    Row(
                        modifier = Modifier
                            .padding(vertical = 12.dp, horizontal = 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_found_item), // Icon baru untuk laporan
                            contentDescription = "Laporan Penemuan",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Laporan\nPenemuan",
                            fontSize = 14.sp,
                            color = Color.White,
                            lineHeight = 16.sp // Menjaga dua baris tetap dalam satu ukuran
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun TopHomeSectionPreview() {
    FindUINSATheme {
        TopHomeSection(searchQuery = "Cari barang", onSearchQueryChanged = {}, onLostReportClick = {}, onFoundReportClick = {})
    }
}

@Composable
fun EmptyPostings() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Belum ada postingan",
            style = MaterialTheme.typography.bodyLarge,
            color = GrayText
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 200)
@Composable
fun EmptyPostingsPreview() {
    FindUINSATheme {
        EmptyPostings()
    }
}

@Composable
fun PostingsList(posts: List<Post>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(posts) { post ->
            PostCard(post = post)
        }
    }
}

@Composable
fun PostCard(post: Post) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Dummy user profile image (for uploaded photos)
                Image(
                    painter = painterResource(id = R.drawable.ic_profile_dummy), // Ganti dengan resource gambar Anda
                    contentDescription = "User Profile",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = post.user,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = post.timeAgo,
                        fontSize = 12.sp,
                        color = GrayText
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .background(
                            color = if (post.type == PostType.FOUND) GreenButton else RedButton,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (post.type == PostType.FOUND) "Penemuan" else "Kehilangan",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = post.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = post.description,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Lokasi sebagai teks biasa
                Text(
                    text = "Lokasi: ${post.location}",
                    fontSize = 12.sp,
                    color = GrayText
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Kategori sebagai teks di dalam shape persegi panjang
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFFF0F0F0), // Warna background untuk kategori
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = post.category,
                        fontSize = 12.sp,
                        color = GrayText
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            post.imageUrl?.let { url ->
                Image(
                    painter = rememberAsyncImagePainter(model = url),
                    contentDescription = "Gambar Postingan",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon komentar
                // Gunakan Icons.AutoMirrored.Filled.Chat jika tidak ada resource drawable
                Icon(
                    painter = painterResource(id = R.drawable.ic_chatadmin), // Pastikan resource ini ada
                    contentDescription = "Chat",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { /* TODO: navigate to chat with admin */ }, // Interaksi klik untuk chat
                    tint = GrayText
                )
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primary, // Warna tombol Klaim
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clickable { /* TODO: Handle claim click */ }
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Klaim",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun PostCardPreview() {
    FindUINSATheme {
    }
}


@Preview(showBackground = true, widthDp = 360, heightDp = 700)
@Composable
fun FullHomeScreenPreview() {
    FindUINSATheme {
        HomeScreen(navController = rememberNavController())
    }
}