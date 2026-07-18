package com.rahulraghuwanshi.matchmate.di.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.rahulraghuwanshi.matchmate.BuildConfig
import com.rahulraghuwanshi.matchmate.data.network.MatchMateApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    /**
     * The method returns the Json (kotlinx.serialization) object
     * */
    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
            coerceInputValues = true
        }
    }


    /**
     * The method returns the Okhttp object
     * */
    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        return httpClient.build()
    }


    /**
     * The method returns the Retrofit object
     * */
    @OptIn(kotlinx.serialization.ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(json: Json, okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory(contentType))
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    /**
     * We need the UserProfileApiInterface.
     * For this, We need the Retrofit object, Json and OkHttpClient.
     * So we will define the providers for these objects here in this module.
     *
     * */
    @Provides
    @Singleton
    fun provideUserProfileApiInterface(retrofit: Retrofit): MatchMateApiInterface {
        return retrofit.create(MatchMateApiInterface::class.java)
    }
}