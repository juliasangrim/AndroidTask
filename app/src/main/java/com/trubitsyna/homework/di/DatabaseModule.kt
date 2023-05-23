package com.trubitsyna.homework.di

import android.content.Context
import androidx.room.Room
import com.trubitsyna.homework.db.NotesDAO
import com.trubitsyna.homework.db.NotesDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DB_NAME = "NotesDatabase"

    @Provides
    @Singleton
    fun providesDB(@ApplicationContext context: Context): NotesDB {
        return Room.databaseBuilder(
            context,
            NotesDB::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesNotesDAO(db: NotesDB): NotesDAO {
        return db.notesDAO()
    }
}