package com.zzh.youchat.network.entity.requestDto

data class RegisterRequest(
    val email: String,
    val password: String,
    val captchaImgUuid: String,
    val captchaCode: String,
    val nickname: String,
)