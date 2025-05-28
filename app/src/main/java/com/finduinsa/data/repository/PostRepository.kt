package com.finduinsa.data.repository

import com.finduinsa.data.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<List<Post>>
    suspend fun addPost(post: Post)
}