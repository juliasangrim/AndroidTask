package com.trubitsyna.homework.domain

import com.trubitsyna.homework.data.model.Note
import com.trubitsyna.homework.data.repository.NotesRepository
import javax.inject.Inject

class AddNoteUseCase @Inject constructor(
    private val notesRepository: NotesRepository,
) {
    suspend fun execute(note: Note) {
        notesRepository.addNotes(note)
    }
}