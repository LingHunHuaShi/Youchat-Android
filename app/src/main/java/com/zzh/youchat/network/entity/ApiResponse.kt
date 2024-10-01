package com.zzh.youchat.network.entity

data class ApiResponse<T>(
    val code: Int,
    val msg: String,
    val data: T
)
