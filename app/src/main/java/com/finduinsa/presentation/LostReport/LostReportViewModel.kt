
package com.finduinsa.presentatio.LostReport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finduinsa.data.model.Post
import com.finduinsa.data.model.PostType
import com.finduinsa.data.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {

    fun submitReport(
        itemName: String,
        itemDescription: String,
        itemCategory: String,
        date: String,
        location: String,
        postType: PostType,
        imageUrl: String? // Tambahkan parameter imageUrl
    ) {
        viewModelScope.launch {
            val newPost = Post(
                id = "",
                user = "Mahasiswa Baru", // Ganti dengan user login
                timeAgo = "Baru saja", // Waktu akan diatur otomatis
                type = postType,
                title = itemName,
                description = itemDescription,
                location = location,
                category = itemCategory,
                imageUrl = imageUrl // Gunakan imageUrl yang diterima
            )
            postRepository.addPost(newPost)
        }
    }
}