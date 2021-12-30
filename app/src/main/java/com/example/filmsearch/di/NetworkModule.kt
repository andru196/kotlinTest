package com.example.filmsearch.di

import com.example.filmsearch.data.network.FilmApi
import com.example.filmsearch.data.network.FilmRepositoryImpl
import com.example.filmsearch.domain.FilmRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.migration.DisableInstallInCheck
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.create

@Module
@DisableInstallInCheck
class NetworkModule {
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

    companion object {
        var apiKey: String = ""
    }

    @Provides
    fun getRepository(filmApi: FilmApi): FilmRepository =
        FilmRepositoryImpl(filmApi, apiKey)

}