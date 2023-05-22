package com.trubitsyna.homework.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.trubitsyna.homework.data.local.model.auth.UserData
import com.trubitsyna.homework.utils.Constants
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataStoreRepositoryImpl @Inject constructor(
    private val userDataStore: DataStore<Preferences>
) : UserDataStoreRepository {

    override suspend fun saveTokenAndUserId(userData: UserData) {
        userDataStore.edit { userInfo ->
            userInfo[stringPreferencesKey(Constants.USER_ID_KEY)] = userData.userId
            userInfo[stringPreferencesKey(Constants.TOKEN_ID_KEY)] = userData.token
        }
    }

    override suspend fun getToken(): String? {
        return userDataStore.data.map { prefs ->
            prefs[stringPreferencesKey(Constants.TOKEN_ID_KEY)]
        }.first()
    }

    override suspend fun getUserId(): String? {
        return userDataStore.data.map { prefs ->
            prefs[stringPreferencesKey(Constants.USER_ID_KEY)]
        }.first()
    }

    override suspend fun deleteTokenAndUserId() {
        userDataStore.edit {
            it.clear()
        }
    }
}