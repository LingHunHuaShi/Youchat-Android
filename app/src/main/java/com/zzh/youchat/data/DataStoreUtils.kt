package com.zzh.youchat.data

import android.content.Context
import android.provider.ContactsContract.Data
import androidx.datastore.preferences.core.edit
import com.zzh.youchat.constants.DataStoreKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object DataStoreUtils {
    suspend fun saveLoginStatus(context: Context, isLoggedIn: Boolean) {
        context.userDataStore.edit { userData ->
            userData[DataStoreKeys.LOGIN_STATE] = isLoggedIn
        }
    }

    fun getLoginStatus(context: Context): Flow<Boolean> {
        return context.userDataStore.data.map { userData ->
            userData[DataStoreKeys.LOGIN_STATE] ?: false
        }
    }

    suspend fun saveUserToken(context: Context, token: String) {
        context.userDataStore.edit { userData ->
            userData[DataStoreKeys.USER_TOKEN] = token
        }
    }

    fun getUserToken(context: Context): Flow<String> {
        return context.userDataStore.data.map { userData ->
            userData[DataStoreKeys.USER_TOKEN] ?: ""
        }
    }

    suspend fun saveServerAddress(context: Context, address: String) {
        context.settingsDataStore.edit { settings ->
            settings[DataStoreKeys.SERVER_ADDRESS] = address
        }
    }

    fun getServerAddress(context: Context): Flow<String> {
        return context.settingsDataStore.data.map { settings ->
            settings[DataStoreKeys.SERVER_ADDRESS] ?: "http://127.0.0.1"
        }
    }

    suspend fun saveIsAdmin(context: Context, isAdmin: Boolean) {
        context.settingsDataStore.edit { settings ->
            settings[DataStoreKeys.IS_ADMIN] = isAdmin
        }
    }

    fun getIsAdmin(context: Context): Flow<Boolean> {
        return context.settingsDataStore.data.map { settings ->
            settings[DataStoreKeys.IS_ADMIN] ?: false
        }
    }

    suspend fun saveUid(context: Context, uid: String) {
        context.userDataStore.edit { userData ->
            userData[DataStoreKeys.UID] = uid
        }
    }

    fun getUid(context: Context): Flow<String> {
        return context.userDataStore.data.map { userData ->
            userData[DataStoreKeys.UID] ?: ""
        }
    }

}