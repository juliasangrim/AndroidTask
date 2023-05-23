package com.trubitsyna.homework.domain

import com.trubitsyna.homework.data.Note
import com.trubitsyna.homework.data.repository.NotesRepository
import com.trubitsyna.homework.data.repository.NotesRepositoryImpl
import kotlinx.coroutines.flow.Flow

class GetNotesUseCase(

    private val notesRepository: NotesRepository = NotesRepositoryImpl()
) {
    fun execute(): Flow<List<Note>> {
        return notesRepository.getNotes()
    }
}

