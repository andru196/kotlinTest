package com.example.filmsearch.data.network.enity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class SearchBody(
    @SerialName("year_from")
    val yearFrom: Int?,
    @SerialName("year_to")
    val yearTo: Int?
)