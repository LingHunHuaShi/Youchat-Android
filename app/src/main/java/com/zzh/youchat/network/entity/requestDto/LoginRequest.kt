package com.zzh.youchat.network.entity.requestDto

data class LoginRequest(
    val email: String,
    val password: String,
    val captchaImgUuid: String,
    val captchaCode: String,
)