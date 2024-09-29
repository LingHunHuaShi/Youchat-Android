package com.zzh.youchat.network

import okhttp3.OkHttpClient

object HttpClientProvider {
    val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }
}