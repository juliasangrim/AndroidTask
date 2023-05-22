package com.trubitsyna.homework.domain.profile

import com.trubitsyna.homework.data.local.model.Profile
import com.trubitsyna.homework.data.repository.ProfileRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val repository: ProfileRepository
) {
    suspend fun execute(profileId: String): Profile {
        return repository.getProfile(profileId)
    }
}