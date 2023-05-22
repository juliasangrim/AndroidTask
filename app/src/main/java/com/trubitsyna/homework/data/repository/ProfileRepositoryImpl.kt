package com.trubitsyna.homework.data.repository

import com.trubitsyna.homework.data.local.model.Profile
import com.trubitsyna.homework.data.mapper.ProfileMapper
import com.trubitsyna.homework.data.remote.NanoPostApiService
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiService: NanoPostApiService,
    private val profileMapper: ProfileMapper
) : ProfileRepository {
    override suspend fun getProfile(profileId: String): Profile {
        return profileMapper.apiProfileToProfile(apiService.getProfile(profileId))
    }
}