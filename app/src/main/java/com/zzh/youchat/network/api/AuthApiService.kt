package com.zzh.youchat.network.api

import com.zzh.youchat.data.entity.ApiResponse
import com.zzh.youchat.data.entity.Captcha
import retrofit2.Response
import retrofit2.http.GET

interface AuthApiService {
    @GET("api/auth/captcha")
    suspend fun getCaptcha(): Response<ApiResponse<Captcha>>
    
}