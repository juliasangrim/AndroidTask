package com.trubitsyna.homework.domain

import com.trubitsyna.homework.data.repository.NotesRepository
import com.trubitsyna.homework.data.repository.NotesRepositoryImpl

class DeleteNoteUseCase(
    private val noteRepository: NotesRepository = NotesRepositoryImpl()
) {
    suspend fun execute(id: String) {
        noteRepository.deleteNotes(id)
    }
}