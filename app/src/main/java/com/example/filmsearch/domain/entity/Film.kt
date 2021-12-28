package com.example.filmsearch.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film (
    val name: String,
    val year: String
) : Parcelable
