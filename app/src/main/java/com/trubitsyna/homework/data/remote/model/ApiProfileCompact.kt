package com.trubitsyna.homework.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiProfileCompact(
    val id: String,
    val username: String,
    val displayName: String? = null,
    val avatarUrl: String? = null,
    val subscribed: Boolean
)