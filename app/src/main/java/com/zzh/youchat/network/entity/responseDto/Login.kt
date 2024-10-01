package com.zzh.youchat.network.entity.responseDto

data class Login (
    val uid: String,
    val token: String,
    val email: String,
    val nickname: String,
    val isAdmin: Boolean,
)