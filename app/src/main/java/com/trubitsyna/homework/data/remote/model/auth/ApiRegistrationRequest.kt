package com.trubitsyna.homework.data.remote.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class ApiRegistrationRequest(
    val username: String,
    val password: String,
)