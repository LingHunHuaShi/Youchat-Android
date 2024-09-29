package com.zzh.youchat.data.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zzh.youchat.data.DataStoreUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class SettingsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
): ViewModel() {
    var serverAddress  = DataStoreUtils.getServerAddress(context)
        .stateIn(viewModelScope, SharingStarted.Lazily, "")

    open fun saveServerAddress(address: String) {
        viewModelScope.launch {
            DataStoreUtils.saveServerAddress(context, address)
        }
    }
}