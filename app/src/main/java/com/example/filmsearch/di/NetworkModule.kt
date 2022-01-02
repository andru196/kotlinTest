package com.example.filmsearch.di

import android.content.Context
import android.content.SharedPreferences
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
import dagger.hilt.migration.DisableInstallInCheck
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.create
import retrofit2.http.Field
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {
    companion object {
        @Provides
        fun provideFilmApi(): FilmApi = Retrofit.Builder()
            .baseUrl("https://kinopoiskapiunofficial.tech")
            .addConverterFactory(
                Json(builderAction = {
                isLenient = true //не оч требовательно,
                ignoreUnknownKeys = true
               }).asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create()

        @Provides
        @Singleton
        @Named("apiKey")
        fun provideApiKeyString(@ApplicationContext context: Context): String {
            val ai = context.packageManager
                .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            val value = ai.metaData["keyValue"]
            return value.toString()
        }
    }

    @Binds
    abstract fun getRepository(filmRepository: FilmRepositoryImpl
    ): FilmRepository

}