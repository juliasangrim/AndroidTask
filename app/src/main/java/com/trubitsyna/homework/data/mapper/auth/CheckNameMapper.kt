package com.trubitsyna.homework.data.mapper.auth

import com.trubitsyna.homework.data.local.model.auth.CheckNameResponse
import com.trubitsyna.homework.data.remote.model.auth.ApiCheckNameResponseEnum
import com.trubitsyna.homework.data.remote.model.auth.ApiValidateUsernameResponse
import javax.inject.Inject

class CheckNameMapper @Inject constructor() {

    fun apiValidateResponseToCheckNameResponse(apiValidateResponse: ApiValidateUsernameResponse)
            : CheckNameResponse {
        return when (apiValidateResponse.result) {
            ApiCheckNameResponseEnum.FREE -> CheckNameResponse.FREE
            ApiCheckNameResponseEnum.SHORT -> CheckNameResponse.SHORT_NAME
            ApiCheckNameResponseEnum.LONG -> CheckNameResponse.LONG_NAME
            ApiCheckNameResponseEnum.INVALID_CHAR -> CheckNameResponse.INVALID_CHAR_NAME
            ApiCheckNameResponseEnum.TAKEN -> CheckNameResponse.TAKEN
        }
    }
}