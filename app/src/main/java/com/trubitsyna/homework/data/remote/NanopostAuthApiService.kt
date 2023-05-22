package com.trubitsyna.homework.data.remote

import com.trubitsyna.homework.data.remote.model.auth.ApiRegistrationRequest
import com.trubitsyna.homework.data.remote.model.auth.ApiTokenResponse
import com.trubitsyna.homework.data.remote.model.auth.ApiValidateUsernameResponse
import com.trubitsyna.homework.di.NetworkModule
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

@NetworkModule.AuthClient
interface NanopostAuthApiService {

    @GET("/api/auth/checkUsername")
    suspend fun checkUsername(
        @Query("username") username: String,
    ): ApiValidateUsernameResponse

    @GET("/api/auth/login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String,
    ): ApiTokenResponse

    @POST("/api/auth/register")
    suspend fun register(
        @Body registerRequest: ApiRegistrationRequest,
    ): ApiTokenResponse
}