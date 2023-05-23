package com.trubitsyna.homework.mapper

import androidx.core.net.toUri
import com.trubitsyna.homework.data.model.Note
import com.trubitsyna.homework.db.model.NoteEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesMapper @Inject constructor() {
    fun fromEntityToUiModel(entity: NoteEntity) = Note(
        id = entity.id,
        text = entity.text,
        imageUri = entity.imageUri?.toUri()
    )

    fun fromUiModelToEntity(note: Note) = NoteEntity(
        id = note.id,
        text = note.text,
        imageUri = note.imageUri?.let { note.imageUri.toString() }
    )
}