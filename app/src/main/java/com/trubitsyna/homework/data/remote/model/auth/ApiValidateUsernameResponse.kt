package com.trubitsyna.homework.data.remote.model.auth

import kotlinx.serialization.Serializable

@Serializable
data class ApiValidateUsernameResponse(
    val result: ApiCheckNameResponseEnum,
)