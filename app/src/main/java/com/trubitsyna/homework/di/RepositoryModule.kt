package com.trubitsyna.homework.di

import com.trubitsyna.homework.data.repository.ImageRepository
import com.trubitsyna.homework.data.repository.ImageRepositoryImpl
import com.trubitsyna.homework.data.repository.NotesRepository
import com.trubitsyna.homework.data.repository.NotesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindNotesRepository(impl: NotesRepositoryImpl) : NotesRepository

    @Binds
    abstract fun bindImageRepository(impl: ImageRepositoryImpl) : ImageRepository
}