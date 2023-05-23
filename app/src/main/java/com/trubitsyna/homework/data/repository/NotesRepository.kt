package com.trubitsyna.homework.data.repository

import com.trubitsyna.homework.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getNotes(): Flow<List<Note>>
    suspend fun addNotes(note: Note)
    suspend fun deleteNotes(note: Note)
}