package com.trubitsyna.homework.data.remote.model.auth

import kotlinx.serialization.SerialName


enum class ApiCheckNameResponseEnum(
) {
    @SerialName("TooShort")
    SHORT,

    @SerialName("TooLong")
    LONG,

    @SerialName("InvalidCharacters")
    INVALID_CHAR,

    @SerialName("Taken")
    TAKEN,

    @SerialName("Free")
    FREE,
}