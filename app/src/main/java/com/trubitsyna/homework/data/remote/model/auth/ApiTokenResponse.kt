package com.trubitsyna.homework.data.remote.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class ApiTokenResponse(
    val token: String,
    val userId: String,
)
