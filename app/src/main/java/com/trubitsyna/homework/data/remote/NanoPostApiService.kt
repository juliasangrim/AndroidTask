package com.trubitsyna.homework.data.remote

import com.trubitsyna.homework.data.remote.model.ApiImage
import com.trubitsyna.homework.data.remote.model.ApiPost
import com.trubitsyna.homework.data.remote.model.ApiProfile
import com.trubitsyna.homework.data.remote.model.ApiResultResponse
import com.trubitsyna.homework.data.remote.model.PagedDataResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface NanoPostApiService {
    @GET("/api/v1/profile/{profileId}")
    suspend fun getProfile(
        @Path("profileId") profileId: String
    ): ApiProfile

    @GET("/api/v1/feed")
    suspend fun getFeed(
        @Query("count") count: Int,
        @Query("offset") offset: String?
    ): PagedDataResponse<ApiPost>

    @GET("/api/v1/posts/{profileId}")
    suspend fun getProfilePosts(
        @Path("profileId") profileId: String,
        @Query("count") count: Int,
        @Query("offset") offset: String?
    ): PagedDataResponse<ApiPost>

    @GET("/api/v1/post/{postId}")
    suspend fun getPost(
        @Path("postId") postId: String,
    ): ApiPost

    @GET("/api/v1/images/{profileId}")
    suspend fun getProfileImages(
        @Path("profileId") profileId: String,
        @Query("count") count: Int,
        @Query("offset") offset: String?,
    ): PagedDataResponse<ApiImage>

    @GET("/api/v1/image/{imageId}")
    suspend fun getImage(
        @Path("imageId") imageId: String,
    ): ApiImage

    @PUT("/api/v1/post")
    @Multipart
    suspend fun createPost(
        @Part("text") text: RequestBody?,
        @Part image1: MultipartBody.Part?,
        @Part image2: MultipartBody.Part?,
        @Part image3: MultipartBody.Part?,
        @Part image4: MultipartBody.Part?
    ): ApiPost

    @DELETE("/api/v1/post/{postId}")
    suspend fun deletePost(
        @Path("postId") postId: String,
    ): ApiResultResponse

    @DELETE("/api/v1/image/{imageId}")
    suspend fun deleteImage(
        @Path("imageId") imageId: String,
    ): ApiResultResponse
}