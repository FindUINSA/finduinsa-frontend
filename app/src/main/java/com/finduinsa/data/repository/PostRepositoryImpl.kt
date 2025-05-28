package com.finduinsa.data.repository

import com.finduinsa.data.model.Post
import com.finduinsa.data.model.PostType
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

@Singleton
class PostRepositoryImpl @Inject constructor() : PostRepository {
    private val _posts = MutableStateFlow(
        listOf(
            Post(
                id = "1",
                user = "Gopal",
                timeAgo = "2 jam",
                type = PostType.FOUND,
                title = "Headphone",
                description = "Lorem ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.",
                location = "Depan lift darurat",
                category = "Elektronik",
                imageUrl = "https://via.placeholder.com/150/0000FF/808080?text=Headphone"
            ),
            Post(
                id = "2",
                user = "Samsul",
                timeAgo = "2 jam",
                type = PostType.LOST,
                title = "Headphone",
                description = "Lorem ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.",
                location = "Depan lift darurat",
                category = "Elektronik",
                imageUrl = "https://via.placeholder.com/150/FF0000/FFFFFF?text=Headphone"
            )
        )
    )

    override fun getPosts(): Flow<List<Post>> = _posts.asStateFlow()

    override suspend fun addPost(post: Post) {
        val newPostWithId = post.copy(id = UUID.randomUUID().toString())
        _posts.update { currentList ->
            listOf(newPostWithId) + currentList // Tambahkan di awal daftar
        }
    }
}