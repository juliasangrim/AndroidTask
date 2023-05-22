package com.trubitsyna.homework.data.repository

import com.trubitsyna.homework.data.local.model.auth.CheckNameResponse
import com.trubitsyna.homework.data.local.model.auth.UserData

interface AuthRepository {
    suspend fun checkUsername(username: String): CheckNameResponse
    suspend fun register(username: String, password: String): UserData
    suspend fun login(username: String, password: String): UserData
}