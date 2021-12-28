package com.example.filmsearch.domain

import com.example.filmsearch.domain.entity.Film
import com.example.filmsearch.domain.entity.TopType

interface FilmRepository {
    /*
    * Запрашивает список топ фильмов по типу [topType]
    * */
    suspend fun getTopFilms(topType: TopType, page:Int): List<Film>
}