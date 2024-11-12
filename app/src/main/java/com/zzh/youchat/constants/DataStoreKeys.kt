package com.zzh.youchat.constants

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val DATA_STORE_NAME = stringPreferencesKey("data_store_name")
    val LOGIN_STATE = booleanPreferencesKey("is_logged_in")
    val USER_TOKEN = stringPreferencesKey("user_token")
    val SERVER_ADDRESS = stringPreferencesKey("server_address")
    val IS_ADMIN = booleanPreferencesKey("is_admin")
    val UID = stringPreferencesKey("uid")
    val EMAIL = stringPreferencesKey("email")
    val NICKNAME = stringPreferencesKey("nickname")
}
