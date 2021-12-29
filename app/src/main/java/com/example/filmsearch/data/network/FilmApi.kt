package com.example.filmsearch.data.network

import com.example.filmsearch.data.network.enity.TopFilmsResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface FilmApi {
    @GET("/api/v2.2/films/top")
    suspend fun getTopFilms(
        @Query("type") type: String,
        @Query("page") page: Int,
        @Header("X-API-KEY") key: String
    ) : TopFilmsResponse
}