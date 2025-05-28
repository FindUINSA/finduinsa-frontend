// app/src/main/java/com/finduinsa/data/model/Post.kt
package com.finduinsa.data.model

data class Post(
    val id: String,
    val user: String,
    val timeAgo: String,
    val type: PostType,
    val title: String,
    val description: String,
    val location: String,
    val category: String,
    val imageUrl: String? = null, // Pastikan ini nullable
    val isClaimed: Boolean = false
)

enum class PostType {
    LOST, FOUND
}