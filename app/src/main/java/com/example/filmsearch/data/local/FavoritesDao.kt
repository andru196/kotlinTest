package com.example.filmsearch.data.local

import com.example.filmsearch.domain.entity.Film
import kotlinx.coroutines.flow.Flow

interface FavoritesDao {
    fun add(film: Film)

    fun delete(film:Film)

    fun isInFavorites(film: Film): Boolean

    fun getFavorites(): Flow<List<Film>>

    fun getCount(): Flow<Int>
}