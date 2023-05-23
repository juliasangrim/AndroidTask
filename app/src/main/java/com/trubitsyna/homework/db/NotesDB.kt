package com.trubitsyna.homework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.trubitsyna.homework.db.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NotesDB: RoomDatabase() {
    abstract fun notesDAO(): NotesDAO
}