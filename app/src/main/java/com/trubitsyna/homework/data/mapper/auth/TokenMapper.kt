package com.trubitsyna.homework.data.mapper.auth

import com.trubitsyna.homework.data.local.model.auth.UserData
import com.trubitsyna.homework.data.remote.model.auth.ApiTokenResponse
import javax.inject.Inject

class TokenMapper @Inject constructor() {
    fun apiTokenResponseToUserData(tokenResponse: ApiTokenResponse): UserData {
        return UserData(
            token = tokenResponse.token,
            userId = tokenResponse.userId,
        )
    }
}