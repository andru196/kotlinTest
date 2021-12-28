package com.example.filmsearch.data.network.enity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Genre(
    @SerialName("genre")
    val genre: String?
)