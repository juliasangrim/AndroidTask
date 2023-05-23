package com.trubitsyna.homework.data.repository

import com.trubitsyna.homework.data.Note
import com.trubitsyna.homework.data.NotesDataSource
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(
    private val notesDataSource: NotesDataSource = NotesDataSource
) : NotesRepository {
    override fun getNotes(): Flow<List<Note>> {
        return notesDataSource.getNotes()
    }

    override suspend fun addNotes(text: String) {
        notesDataSource.addNote(text)
    }

    override suspend fun deleteNotes(id: String) {
        notesDataSource.deleteNote(id)
    }
}