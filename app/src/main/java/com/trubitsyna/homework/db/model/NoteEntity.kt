package com.trubitsyna.homework.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val text: String,
    val imageUri: String?,
)
