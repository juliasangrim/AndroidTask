package com.trubitsyna.homework.domain.auth

import com.trubitsyna.homework.data.local.model.auth.UserData
import com.trubitsyna.homework.data.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepositoryImpl: AuthRepository,
) {
    suspend fun execute(username: String, password: String): UserData {
        return authRepositoryImpl.register(username, password)
    }
}