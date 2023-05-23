package com.trubitsyna.homework.domain

import com.trubitsyna.homework.data.model.Note
import com.trubitsyna.homework.data.repository.NotesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val notesRepository: NotesRepository,
) {
    fun execute(): Flow<List<Note>> {
        return notesRepository.getNotes()
    }
}

