package com.example.filmsearch.data.network

import com.example.filmsearch.domain.GeoRepository
import com.example.filmsearch.domain.entity.City
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeoRepositoryImpl @Inject constructor(
    private val geoApi: GeoApi
) : GeoRepository {
    override suspend fun getCity(lat: Double, lon: Double): City = withContext(Dispatchers.IO) {
        val resp = geoApi.getCity(lat, lon)
        City(
            name = resp.address?.city ?: "",
            country = resp.address?.country ?: ""
        )
    }
}