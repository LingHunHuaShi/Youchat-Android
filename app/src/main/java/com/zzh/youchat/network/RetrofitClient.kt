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
    private fun getBaseUrl(context: Context): String {
        return runBlocking {
            DataStoreUtils.getServerAddress(context).first()
        }
    }

    fun <T> getRetrofitInstance(context: Context, serviceClass: Class<T>): T{
        val baseUrl = getBaseUrl(context)
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(HttpClientProvider.client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(serviceClass)
    }

}