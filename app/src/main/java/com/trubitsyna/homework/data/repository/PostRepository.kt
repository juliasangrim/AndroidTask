package com.trubitsyna.homework.data.repository

import androidx.paging.PagingData
import com.trubitsyna.homework.data.local.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getFeed(
        loadExceptionCallback: () -> Unit
    ): Flow<PagingData<Post>>

    fun getProfilePosts(
        profileId: String,
        loadExceptionCallback: () -> Unit
    ): Flow<PagingData<Post>>

    suspend fun getPost(postId: String): Post
    suspend fun createPost(text: String?, list: List<ByteArray>?): Post
    suspend fun deletePost(postId: String)
}