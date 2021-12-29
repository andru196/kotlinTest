package com.example.filmsearch.data.network

import com.example.filmsearch.domain.FilmRepository
import com.example.filmsearch.domain.entity.Film
import com.example.filmsearch.domain.entity.TopType

class FilmRepositoryImpl(
    private val filmApi: FilmApi,
    private val apiKey: String
) : FilmRepository {
    override suspend fun getTopFilms(topType: TopType, page: Int): List<Film> {
        return filmApi.getTopFilms(topType.serverType, page, apiKey).films?.mapNotNull { filmNw ->
            Film(name=filmNw.nameRu ?: return@mapNotNull null,
            year = filmNw.year?.toIntOrNull() ?: return@mapNotNull null,
            posterUrl = filmNw.posterUrl ?: "",
            posterUrlPreview =  filmNw.posterUrlPreview ?: "",
            rating = filmNw.rating)
        } ?: emptyList()
    }

}