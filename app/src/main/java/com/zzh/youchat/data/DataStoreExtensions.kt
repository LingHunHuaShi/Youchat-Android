package com.zzh.youchat.data

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.zzh.youchat.constants.DataStoreKeys

val Context.userDataStore by preferencesDataStore(name = "user")
val Context.settingsDataStore by preferencesDataStore(name = "settings")