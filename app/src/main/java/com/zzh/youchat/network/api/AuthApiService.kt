package com.zzh.youchat.network.api

import androidx.datastore.preferences.protobuf.Api
import com.zzh.youchat.network.entity.ApiResponse
import com.zzh.youchat.network.entity.responseDto.Captcha
import com.zzh.youchat.network.entity.responseDto.Login
import com.zzh.youchat.network.entity.requestDto.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthApiService {
    @GET("api/auth/captcha")
    suspend fun getCaptcha(): Response<ApiResponse<Captcha>>

    @POST("api/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<ApiResponse<Login>>

    @POST("api/auth/logout")
    suspend fun logout(
        @Query("uid") uid: String
    ): Response<ApiResponse<String>>
}