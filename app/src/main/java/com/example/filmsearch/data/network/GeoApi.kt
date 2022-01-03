package com.example.filmsearch.data.network

import com.example.filmsearch.data.network.enity.GeoCityResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface GeoApi {
    @GET("reverse")
    suspend fun getCity(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("format") format:String = "json"
    ) : GeoCityResponse
}