package com.trubitsyna.homework.data.repository

import com.trubitsyna.homework.data.local.model.Profile

interface ProfileRepository {
    suspend fun getProfile(profileId: String): Profile
}