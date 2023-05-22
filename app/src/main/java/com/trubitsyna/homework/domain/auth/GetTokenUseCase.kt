package com.trubitsyna.homework.domain.auth

import com.trubitsyna.homework.data.repository.UserDataStoreRepository
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(
    private val dataStoreRepositoryImpl: UserDataStoreRepository,
) {

    suspend fun execute(): String? {
        return dataStoreRepositoryImpl.getToken()
    }
}