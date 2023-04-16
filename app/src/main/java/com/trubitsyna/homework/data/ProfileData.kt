package com.trubitsyna.homework.data

import java.util.UUID

data class ProfileData(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val subtext: String,
    val imageCount: Int,
    val subscribeCount: Int,
    val postCount: Int
)
