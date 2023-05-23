package com.trubitsyna.homework.domain

import com.trubitsyna.homework.data.repository.NotesRepository
import com.trubitsyna.homework.data.repository.NotesRepositoryImpl

class AddNoteUseCase(
    private val notesRepository: NotesRepository = NotesRepositoryImpl()
) {
    suspend fun execute(text: String) {
        notesRepository.addNotes(text)
    }

}