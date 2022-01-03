package com.example.filmsearch.di

import android.content.Context
import android.content.pm.PackageManager
import com.example.filmsearch.data.network.FilmApi
import com.example.filmsearch.data.network.FilmRepositoryImpl
import com.example.filmsearch.domain.FilmRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    companion object {
        @Provides
        fun provideFilmApi(): FilmApi = Retrofit.Builder()
            .baseUrl("https://kinopoiskapiunofficial.tech")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor { chain: Interceptor.Chain ->
                        val req = chain.request()
                            .newBuilder()
                            .addHeader("X-API-KEY", apiKey)
                            .build()
                        chain.proceed(req)
                    }
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                    .connectTimeout(10, TimeUnit.SECONDS)
//                    .callTimeout(10, TimeUnit.SECONDS)
//                    .readTimeout(10, TimeUnit.SECONDS)
//                    .writeTimeout(10, TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(
                Json(builderAction = {
                isLenient = true //не оч требовательно,
                ignoreUnknownKeys = true
               }).asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create()
        var apiKey: String = ""
        @Provides
        @Singleton
        @Named("apiKey")
        fun provideApiKeyString(@ApplicationContext context: Context): String {
            val ai = context.packageManager
                .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            val value = ai.metaData["keyValue"]
            apiKey = value.toString()
            return apiKey
        }
    }

    @Binds
    abstract fun getRepository(filmRepository: FilmRepositoryImpl
    ): FilmRepository

}