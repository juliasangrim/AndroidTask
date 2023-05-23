package com.trubitsyna.homework.data

import java.util.UUID

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val text: String
)