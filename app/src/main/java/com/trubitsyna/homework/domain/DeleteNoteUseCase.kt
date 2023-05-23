package com.trubitsyna.homework.domain

import com.trubitsyna.homework.data.model.Note
import com.trubitsyna.homework.data.repository.NotesRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val noteRepository: NotesRepository,
) {
    suspend fun execute(note: Note) {
        noteRepository.deleteNotes(note)
    }
}