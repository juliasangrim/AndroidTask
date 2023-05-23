package com.trubitsyna.homework.data.model

import android.net.Uri

data class Note(
    val id: Long,
    val text: String,
    val imageUri: Uri?
)