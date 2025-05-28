package com.finduinsa.presentation.home


import com.finduinsa.data.model.Post
import com.finduinsa.data.model.PostType

data class HomeState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedPostType: PostType? = null // null untuk semua, LOST, atau FOUND
)