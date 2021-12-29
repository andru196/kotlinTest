package com.example.filmsearch.di

import com.example.filmsearch.data.network.FilmApi
import com.example.filmsearch.data.network.FilmRepositoryImpl
import com.example.filmsearch.domain.FilmRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.create

object NetworkModule {
    private val filmApi: FilmApi = Retrofit.Builder()
        .baseUrl("https://kinopoiskapiunofficial.tech")
        .addConverterFactory(
            Json(builderAction = {
                isLenient = true //не оч требовательно,
                ignoreUnknownKeys = true
            }).asConverterFactory("application/json".toMediaType())
        )
        .build()
        .create()

    private var repository:FilmRepository? = null
    var apiKey: String = ""

    fun getRepository(): FilmRepository {
        if (apiKey.isBlank())
            throw ExceptionInInitializerError()
        return repository ?: FilmRepositoryImpl(filmApi, apiKey).also { repository = it }
    }
}