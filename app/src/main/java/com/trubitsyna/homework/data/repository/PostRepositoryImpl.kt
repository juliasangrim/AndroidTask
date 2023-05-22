package com.trubitsyna.homework.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.trubitsyna.homework.data.local.model.Post
import com.trubitsyna.homework.data.mapper.PostMapper
import com.trubitsyna.homework.data.paging.FeedPagingSource
import com.trubitsyna.homework.data.paging.PostsPagingSource
import com.trubitsyna.homework.data.remote.NanoPostApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val nanoPostApiService: NanoPostApiService,
    private val mapper: PostMapper
) : PostRepository {
    override fun getFeed(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(30, enablePlaceholders = false),
            pagingSourceFactory = {
                FeedPagingSource(
                    nanoPostApiService
                )
            }
        ).flow.map { value ->
            value.map {
                mapper.apiPostToPost(it)
            }
        }
    }

    override fun getProfilePosts(
        profileId: String
    ): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(30, enablePlaceholders = false),
            pagingSourceFactory = {
                PostsPagingSource(
                    profileId,
                    nanoPostApiService
                )
            }
        ).flow.map { value ->
            value.map {
                mapper.apiPostToPost(it)
            }
        }
    }

    override suspend fun getPost(postId: String): Post {
        return mapper.apiPostToPost(nanoPostApiService.getPost(postId))
    }

    override suspend fun createPost(text: String?, list: List<ByteArray>?): Post {
        val image = "image/*"
        var image0: MultipartBody.Part? = null
        list?.getOrNull(0)?.let {
            image0 = MultipartBody.Part.createFormData(
                "image1",
                "image1.jpg",
                it.toRequestBody(image.toMediaType())
            )
        }
        var image1: MultipartBody.Part? = null
        list?.getOrNull(1)?.let {
            image1 = MultipartBody.Part.createFormData(
                "image2",
                "image2.jpg",
                it.toRequestBody(image.toMediaType())
            )
        }
        var image2: MultipartBody.Part? = null
        list?.getOrNull(2)?.let {
            image2 = MultipartBody.Part.createFormData(
                "image3",
                "image3.jpg",
                it.toRequestBody(image.toMediaType())
            )
        }
        var image3: MultipartBody.Part? = null
        list?.getOrNull(3)?.let {
            image3 = MultipartBody.Part.createFormData(
                "image4",
                "image4.jpg",
                it.toRequestBody(image.toMediaType())
            )
        }
        return mapper.apiPostToPost(
            nanoPostApiService.createPost(
                text?.toRequestBody(),
                image0,
                image1,
                image2,
                image3
            )
        )
    }

    override suspend fun deletePost(postId: String): Boolean {
        return nanoPostApiService.deletePost(postId).result
    }
}