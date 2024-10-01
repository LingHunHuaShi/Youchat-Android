package com.zzh.youchat.network

import android.content.Context
import com.zzh.youchat.data.DataStoreUtils
import com.zzh.youchat.network.api.AuthApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    private var retrofit: Retrofit? = null
    private var currentUrl: String? = null

    private suspend fun getBaseUrl(context: Context): String {
        return DataStoreUtils.getServerAddress(context).first()
    }

    fun <T> getRetrofitInstance(context: Context, serviceClass: Class<T>): T{
        val baseUrl = runBlocking{ getBaseUrl(context) }
        if (retrofit == null || currentUrl != baseUrl) {
            retrofit =  Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(HttpClientProvider.client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(serviceClass)
    }

    fun resetRetrofitClient() {
        retrofit = null
        currentUrl = null
    }

}