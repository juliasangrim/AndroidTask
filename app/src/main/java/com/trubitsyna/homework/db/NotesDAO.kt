package com.trubitsyna.homework.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.trubitsyna.homework.db.model.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDAO {

    @Query("SELECT * FROM notes")
    fun getNotes(): Flow<List<NoteEntity>>

    @Insert
    fun addNote(note: NoteEntity)

    @Delete
    fun deleteNote(note: NoteEntity)
}