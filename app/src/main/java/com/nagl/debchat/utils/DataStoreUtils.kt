package com.nagl.debchat.utils

// thanks: https://proandroiddev.com/securing-androids-datastore-ad56958ca6ee

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class DataStoreUtils
@Inject
constructor(
    private val dataStore: DataStore<Preferences>,
    private val security: SecurityUtil
) {
    private val securityKeyAlias = "data-store"
    private val bytesToStringSeparator = "|"

//    fun getData() = dataStore.data
//        .map { preferences ->
//            preferences[DATA].orEmpty()
//        }
//
//    suspend fun setData(value: String) {
//        dataStore.edit {
//            it[DATA] = value
//        }
//    }

    fun getSecuredToken() = dataStore.data
        .secureMap<String> { preferences ->
            preferences[USER_TOKEN].orEmpty()
        }

    suspend fun setSecuredToken(value: String) {
        dataStore.secureEdit(value) { prefs, encryptedValue ->
            prefs[USER_TOKEN] = encryptedValue
        }
    }

    suspend fun hasKey(key: Preferences.Key<*>) = dataStore.edit { it.contains(key) }

    suspend fun clearDataStore() {
        dataStore.edit {
            it.clear()
        }
    }

    private val json = Json { encodeDefaults = true }

    private inline fun <reified T> Flow<Preferences>.secureMap(crossinline fetchValue: (value: Preferences) -> String): Flow<T> {
        return map { it ->
            val decryptedValue = security.decryptData(
                securityKeyAlias,
                fetchValue(it).split(bytesToStringSeparator).map { it.toByte() }.toByteArray()
            )
            json.decodeFromString(decryptedValue)
        }
    }

    private suspend inline fun <reified T> DataStore<Preferences>.secureEdit(
        value: T,
        crossinline editStore: (MutablePreferences, String) -> Unit
    ) {
        edit {
            val encryptedValue = security.encryptData(securityKeyAlias, Json.encodeToString(value))
            editStore.invoke(it, encryptedValue.joinToString(bytesToStringSeparator))
        }
    }

    companion object {
        val USER_TOKEN = stringPreferencesKey("TOKEN")
        //val IS_USER_AUTH = booleanPreferencesKey("IS_USER_AUTH")
    }
}