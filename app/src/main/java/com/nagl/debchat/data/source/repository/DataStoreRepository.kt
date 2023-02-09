package com.nagl.debchat.data.source.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth_prefs")

class DataStoreRepository (
    private val context: Context,
) {

    val token: Flow<String> = context.dataStore.data.map {
        it[USER_TOKEN] ?: ""
    }

    val isUserAuth: Flow<Boolean> = context.dataStore.data.map {
        it[IS_USER_AUTH] ?: false
    }

    suspend fun saveUserToken(token: String) {
        context.dataStore.edit {
            it[USER_TOKEN] = token
        }
    }

    suspend fun saveUserAuth(isUserAuth: Boolean) {
        context.dataStore.edit {
            it[IS_USER_AUTH] = isUserAuth
        }
    }

    suspend fun clearData() {
        context.dataStore.edit {
            it.clear()
        }
    }

    companion object {
        val USER_TOKEN = stringPreferencesKey("TOKEN")
        val IS_USER_AUTH = booleanPreferencesKey("IS_USER_AUTH")
    }
}