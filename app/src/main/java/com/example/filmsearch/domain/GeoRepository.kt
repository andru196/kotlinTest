package com.example.filmsearch.domain

import com.example.filmsearch.domain.entity.City

interface GeoRepository {
    suspend fun getCity(lat: Double, lon: Double): City
}
