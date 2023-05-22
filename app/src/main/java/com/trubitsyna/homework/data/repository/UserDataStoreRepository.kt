package com.trubitsyna.homework.data.repository

import com.trubitsyna.homework.data.local.model.auth.UserData

interface UserDataStoreRepository {
    suspend fun saveTokenAndUserId(userData: UserData)
    suspend fun getToken(): String?
    suspend fun getUserId(): String?
    suspend fun deleteTokenAndUserId()
}