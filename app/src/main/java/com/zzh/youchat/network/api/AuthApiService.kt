package com.zzh.youchat.network.api

import com.zzh.youchat.network.entity.ApiResponse
import com.zzh.youchat.network.entity.responseDto.Captcha
import com.zzh.youchat.network.entity.responseDto.Login
import com.zzh.youchat.network.entity.requestDto.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {
    @GET("api/auth/captcha")
    suspend fun getCaptcha(): Response<ApiResponse<Captcha>>

    @Headers("Content-Type: application/json")
    @POST("api/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<ApiResponse<Login>>

    @Headers("Content-Type: application/json")
    @POST("api/auth/logout")
    suspend fun logout(
        @Query("uid") uid: String
    ): Response<ApiResponse<String>>
}