package com.trubitsyna.homework.domain.auth

import com.trubitsyna.homework.data.local.model.auth.CheckNameResponse
import com.trubitsyna.homework.data.repository.AuthRepository
import javax.inject.Inject

class CheckUsernameUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun execute(username: String): CheckNameResponse {
        return authRepository.checkUsername(username)
    }
}