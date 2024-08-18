package com.alan.testtask.di

import android.content.Context
import android.content.SharedPreferences
import com.alan.testtask.data.local.db.ProfileDao
import com.alan.testtask.data.local.db.TestTaskDatabase
import com.alan.testtask.data.network.api.ApiFactory
import com.alan.testtask.data.network.api.ApiFactory.Companion.BASE_URL
import com.alan.testtask.data.network.api.ApiService
import com.alan.testtask.data.network.api.AuthInterceptor
import com.alan.testtask.data.network.api.RefreshApiService
import com.alan.testtask.data.repository.AuthRepositoryImpl
import com.alan.testtask.data.repository.ProfileRepositoryImpl
import com.alan.testtask.data.storage.TokenRepositoryImpl
import com.alan.testtask.domain.repository.AuthRepository
import com.alan.testtask.domain.repository.ProfileRepository
import com.alan.testtask.domain.repository.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
interface DataModule {

    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindProfileRepository(impl: ProfileRepositoryImpl): ProfileRepository

    @Binds
    fun bindTokenRepository(impl: TokenRepositoryImpl): TokenRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        }

        @Provides
        @ApplicationScope
        fun provideAuthInterceptor(tokenRepository: TokenRepository): AuthInterceptor {
            return AuthInterceptor(tokenRepository)
        }

        @Provides
        @ApplicationScope
        fun provideApiService(apiFactory: ApiFactory): ApiService {
            return apiFactory.createApiService()
        }

        @Provides
        @ApplicationScope
        fun provideRefreshApiService(): RefreshApiService {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(RefreshApiService::class.java)
        }

        @Provides
        @ApplicationScope
        fun provideFavouriteDatabase(context: Context): TestTaskDatabase =
            TestTaskDatabase.getInstance(context)

        @Provides
        @ApplicationScope
        fun provideFavouriteCitiesDao(favouriteDatabase: TestTaskDatabase): ProfileDao =
            favouriteDatabase.profileDao()
    }
}
