package com.trubitsyna.homework.domain.auth

import com.trubitsyna.homework.data.repository.UserDataStoreRepository
import javax.inject.Inject

class DeleteUserInfoUseCase @Inject constructor(
    private val userInfoRepositoryImpl: UserDataStoreRepository
) {
    suspend fun execute() {
        userInfoRepositoryImpl.deleteTokenAndUserId()
    }
}