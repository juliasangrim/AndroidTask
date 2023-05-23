package com.trubitsyna.homework.data.repository

import com.trubitsyna.homework.data.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getNotes(): Flow<List<Note>>
    suspend fun addNotes(text: String)
    suspend fun deleteNotes(id: String)
}