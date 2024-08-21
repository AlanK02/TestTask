package com.alan.testtask.data.network.api


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class ApiFactory @Inject constructor(
    private val authInterceptor: AuthInterceptor
) {
    fun createApiService(): ApiService {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }

    companion object {
        const val BASE_URL = "https://plannerok.ru"
    }
}
