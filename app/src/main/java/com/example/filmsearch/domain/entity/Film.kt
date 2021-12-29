package com.example.filmsearch.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName

@Parcelize
data class Film (
    val name: String,
    val year: Int,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val rating: String?
) : Parcelable
