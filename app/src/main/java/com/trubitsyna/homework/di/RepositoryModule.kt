package com.trubitsyna.homework.di

import com.trubitsyna.homework.data.repository.AuthRepository
import com.trubitsyna.homework.data.repository.AuthRepositoryImpl
import com.trubitsyna.homework.data.repository.ImageRepository
import com.trubitsyna.homework.data.repository.ImageRepositoryImpl
import com.trubitsyna.homework.data.repository.PostRepository
import com.trubitsyna.homework.data.repository.PostRepositoryImpl
import com.trubitsyna.homework.data.repository.ProfileRepository
import com.trubitsyna.homework.data.repository.ProfileRepositoryImpl
import com.trubitsyna.homework.data.repository.UserDataStoreRepository
import com.trubitsyna.homework.data.repository.UserDataStoreRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    @Singleton
    abstract fun bindPostRepository(impl: PostRepositoryImpl): PostRepository

    @Binds
    @Singleton
    abstract fun bindImageRepository(impl: ImageRepositoryImpl): ImageRepository

    @Binds
    @Singleton
    abstract fun bindUserDataRepository(impl: UserDataStoreRepositoryImpl): UserDataStoreRepository

}