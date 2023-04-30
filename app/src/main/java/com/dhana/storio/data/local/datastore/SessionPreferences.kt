package com.dhana.storio.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionPreferences (private val dataStore: DataStore<Preferences>) {

    private val USER_ID_KEY = stringPreferencesKey("user_id")
    private val USER_NAME_KEY = stringPreferencesKey("user_name")
    private val USER_TOKEN_KEY = stringPreferencesKey("user_token")

    fun getUserId(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[USER_ID_KEY]
        }
    }

    fun getUserName(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[USER_NAME_KEY]
        }
    }

    fun getUserToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[USER_TOKEN_KEY]
        }
    }

    suspend fun saveUserData(userId: String, userName: String, userToken: String) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
            preferences[USER_NAME_KEY] = userName
            preferences[USER_TOKEN_KEY] = userToken
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SessionPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SessionPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SessionPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}