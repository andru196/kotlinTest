package com.example.filmsearch.data.network.enity


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Address(
    @SerialName("city")
    val city: String? = null,
    @SerialName("country")
    val country: String? = null,
    @SerialName("country_code")
    val countryCode: String? = null,
    @SerialName("county")
    val county: String? = null,
    @SerialName("hamlet")
    val hamlet: String? = null,
    @SerialName("house_number")
    val houseNumber: String? = null,
    @SerialName("postcode")
    val postcode: String? = null,
    @SerialName("road")
    val road: String? = null,
    @SerialName("state")
    val state: String? = null,
    @SerialName("state_district")
    val stateDistrict: String? = null,
    @SerialName("town")
    val town: String? = null,
    @SerialName("village")
    val village: String? = null
)