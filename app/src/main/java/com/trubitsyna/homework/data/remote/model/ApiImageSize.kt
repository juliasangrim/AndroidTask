package com.trubitsyna.homework.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiImageSize(
    val width: Int,
    val height: Int,
    val url: String,
)