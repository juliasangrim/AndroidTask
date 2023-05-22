package com.trubitsyna.homework.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.trubitsyna.homework.data.remote.NanoPostApiService
import com.trubitsyna.homework.data.remote.NanopostAuthApiService
import com.trubitsyna.homework.domain.auth.GetTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Converter.Factory
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://nanopost.evolitist.com/"

    @Qualifier
    annotation class AuthClient

    @Qualifier
    annotation class ChuckerClient

    @Provides
    @Singleton
    @AuthClient
    fun provideRetrofit(
        httpClient: OkHttpClient,
        json: Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(
                json
            )
            .build()
    }

    @Provides
    fun provideJson(): Factory {
        return Json {
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json".toMediaType())
    }

    @Provides
    @Singleton
    fun provideAuthRetrofit(
        @ChuckerClient httpClient: OkHttpClient,
        json: Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(
                json
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideNanoPostApiService(
        retrofit: Retrofit
    ): NanopostAuthApiService {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideNanopostAuthApiService(@AuthClient retrofit: Retrofit): NanoPostApiService {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        getTokenUseCase: GetTokenUseCase,
    ): Interceptor {
        return AuthSuspendInterceptor { chain ->
            val token = getTokenUseCase.execute()
            val request = chain.request().newBuilder()
                .addHeader(
                    name = "Authorization",
                    "Bearer $token"
                )
            chain.proceed(request.build())
        }
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        authInterceptor: Interceptor,
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @ChuckerClient
    fun provideChuckerHttpClient(
        @ApplicationContext context: Context
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
            .build()
    }

    @Suppress("FunctionName")
    private fun AuthSuspendInterceptor(
        body: suspend (chain: Interceptor.Chain) -> Response,
    ) = Interceptor {
        runBlocking { body(it) }
    }
}