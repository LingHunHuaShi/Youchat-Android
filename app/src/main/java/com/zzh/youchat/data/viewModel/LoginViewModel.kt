package com.zzh.youchat.data.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import com.zzh.youchat.data.DataStoreUtils
import com.zzh.youchat.network.entity.responseDto.Captcha
import com.zzh.youchat.network.RetrofitClient
import com.zzh.youchat.network.api.AuthApiService
import com.zzh.youchat.network.entity.requestDto.LoginRequest
import com.zzh.youchat.network.entity.responseDto.Login
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
    private val TAG = "LoginViewModel DEBUG"

    var userName = MutableLiveData("")
    var password = MutableLiveData("")
    var captcha = MutableLiveData<Captcha?>(null)
    var errMsg = MutableLiveData("")

    val loginStatus = DataStoreUtils.getLoginStatus(context)
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    val userToken = DataStoreUtils.getUserToken(context)
        .stateIn(viewModelScope, SharingStarted.Lazily, "_INIT_USER_TOKEN_VALUE_")

    val uid = DataStoreUtils.getUid(context)
        .stateIn(viewModelScope, SharingStarted.Lazily, "_INIT_UID_VALUE_")

    private var authApiService = RetrofitClient.getRetrofitInstance(context, AuthApiService::class.java)

    fun clearErrMsg() {
        errMsg.postValue(null)
    }

    fun saveUserToken(token: String) {
        viewModelScope.launch {
            DataStoreUtils.saveUserToken(context, token)
        }
    }

    fun saveLoginStatus(isLoggedIn: Boolean) {
        viewModelScope.launch {
            DataStoreUtils.saveLoginStatus(context, isLoggedIn)
        }
    }

    fun saveUid(uid: String) {
        viewModelScope.launch {
            DataStoreUtils.saveUid(context, uid)
        }
    }

    fun renewApi() {
        RetrofitClient.resetRetrofitClient()
        authApiService = RetrofitClient.getRetrofitInstance(context, AuthApiService::class.java)
    }

    fun fetchCaptcha() {
        Log.d(TAG, "fetchCaptcha11: ")
        viewModelScope.launch {
            try {
                val response = authApiService.getCaptcha()
                Log.d(TAG, "fetchCaptcha22: ")
                if (response.isSuccessful) {
                    captcha.postValue(response.body()?.data)
                } else {
                    errMsg.postValue("Code: ${response.code()}; Error: ${response.errorBody()}")
                    Log.d(TAG, "fetchCaptcha error: ${errMsg.value}")
                }
            } catch (e: Exception) {
                errMsg.postValue("Error: ${e.message}")
                Log.d(TAG, "fetchCaptcha: ${e.message}")
            }
        }
    }

    fun login(loginRequest: LoginRequest, onResult: (Login?) -> Unit) {
        Log.d(TAG, "login: $loginRequest")
        viewModelScope.launch {
            try {
                val response = authApiService.login(loginRequest)
                if (response.isSuccessful) {
                    Log.d(TAG, "login response body: ${response.body()}")
                    onResult(response.body()?.data)
                } else {
                    errMsg.postValue("Code: ${response.code()}; Error: ${response.errorBody()}")
                    Log.d(TAG, "login error: ${errMsg.value}")
                    onResult(null)
                }
            } catch (e: Exception) {
                errMsg.postValue("Error: ${e.message}")
                Log.d(TAG, "login error: ${errMsg.value}")
            }
        }
    }

    fun logout(uid: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = authApiService.logout(uid)
                if (response.isSuccessful) {
                    Log.d(TAG, "logout: successful")
                    onResult(true)
                } else {
                    Log.d(TAG, "logout error: ${response.message()}")
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.d(TAG, "logout error: ${e.message}")
                onResult(false)
            }
        }
    }
}