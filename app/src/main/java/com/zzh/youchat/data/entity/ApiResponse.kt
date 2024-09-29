package com.zzh.youchat.data.entity

data class ApiResponse<T>(
    val code: Int,
    val msg: String,
    val data: T
)
