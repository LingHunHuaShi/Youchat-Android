package com.zzh.youchat.data.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.zzh.youchat.data.DataStoreUtils
import com.zzh.youchat.data.entity.Captcha
import com.zzh.youchat.network.RetrofitClient
import com.zzh.youchat.network.api.AuthApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val imageLoader: ImageLoader,
) : ViewModel() {
    val TAG = "LoginViewModel DEBUG"

    var userName = MutableLiveData("")
    var password = MutableLiveData("")
    var captcha = MutableLiveData<Captcha?>(null)
    var errMsg = MutableLiveData("")

    val loginStatus = DataStoreUtils.getLoginStatus(context)
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun saveLoginStatus(isLoggedIn: Boolean) {
        viewModelScope.launch {
            DataStoreUtils.saveLoginStatus(context, isLoggedIn)
        }
    }

    fun fetchCaptcha(context: Context) {
        Log.d(TAG, "fetchCaptcha11: ")
        viewModelScope.launch {
            val authApiService = RetrofitClient.getRetrofitInstance(context, AuthApiService::class.java)
            try {
                val response = authApiService.getCaptcha()
                Log.d(TAG, "fetchCaptcha22: ")
                if (response.isSuccessful) {
                    captcha.postValue(response.body()?.data)
                } else {
                    errMsg.postValue("Code: ${response.code()}; Error: ${response.errorBody()}")
                }
            } catch (e: Exception) {
                errMsg.postValue("Error: ${e.message}")
                Log.d(TAG, "fetchCaptcha: ${e.message}")
            }
        }
    }
}