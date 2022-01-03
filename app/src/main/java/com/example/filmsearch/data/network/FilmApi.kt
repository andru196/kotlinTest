package com.example.filmsearch.data.network

import com.example.filmsearch.data.network.enity.SearchBody
import com.example.filmsearch.data.network.enity.TopFilmsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FilmApi {
    @GET("/api/v2.2/films/top")
    suspend fun getTopFilms(
        @Query("type") type: String,
        @Query("page") page: Int
    ) : TopFilmsResponse

    @GET("/api/v2.1/films/search-by-filters")
    suspend fun search(
        @Query("yearFrom") yearFrom: Int?,
        @Query("yearTo") yearTo: Int?,
    ) : TopFilmsResponse

//    @POST("/api/v2.1/films/search-by-filters")
//    suspend fun search(
//        @Body() body: SearchBody
//    ) : TopFilmsResponse

    companion object {
        var X_API_KEY: String = ""
    }
}

