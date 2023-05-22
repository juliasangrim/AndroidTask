package com.trubitsyna.homework.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResultResponse(
    val result: Boolean,
)
