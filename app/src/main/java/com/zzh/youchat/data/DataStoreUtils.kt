package com.zzh.youchat.data

import android.content.Context
import android.provider.ContactsContract.Data
import androidx.datastore.preferences.core.edit
import com.zzh.youchat.constants.DataStoreKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object DataStoreUtils {
    suspend fun saveLoginStatus(context: Context, isLoggedIn: Boolean) {
        context.userDataStore.edit { login ->
            login[DataStoreKeys.LOGIN_STATE] = isLoggedIn
        }
    }

    fun getLoginStatus(context: Context): Flow<Boolean> {
        return context.userDataStore.data.map { login ->
            login[DataStoreKeys.LOGIN_STATE] ?: false
        }
    }

    suspend fun saveUserToken(context: Context, token: String) {
        context.userDataStore.edit { login ->
            login[DataStoreKeys.USER_TOKEN] = token
        }
    }

    fun getUserToken(context: Context): Flow<String> {
        return context.userDataStore.data.map { login ->
            login[DataStoreKeys.USER_TOKEN] ?: ""
        }
    }

    suspend fun saveServerAddress(context: Context, address: String) {
        context.settingsDataStore.edit { settings ->
            settings[DataStoreKeys.SERVER_ADDRESS] = address
        }
    }

    fun getServerAddress(context: Context): Flow<String> {
        return context.settingsDataStore.data.map { settings ->
            settings[DataStoreKeys.SERVER_ADDRESS] ?: ""
        }
    }

}