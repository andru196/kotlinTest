package com.example.filmsearch.data.network

import android.content.SharedPreferences
import com.example.filmsearch.data.network.enity.TopFilmsResponse
import com.example.filmsearch.domain.FilmRepository
import com.example.filmsearch.domain.entity.Film
import com.example.filmsearch.domain.entity.TopType
import javax.inject.Inject
import javax.inject.Named

class FilmRepositoryImpl @Inject constructor(
    private val filmApi: FilmApi,
    private val sharedPreferences: SharedPreferences,
    @Named("apiKey") private val apiKey: String
) : FilmRepository {
    override suspend fun getTopFilms(topType: TopType, page: Int): List<Film> {
        return filmApi.getTopFilms(topType.serverType, page).toFilms()
    }

    override suspend fun search(yearFrom:Int?, yearTo:Int?): List<Film> {
        return filmApi.search(yearFrom, yearTo)
            .toFilms()
    }

    private fun TopFilmsResponse.toFilms() : List<Film> =
        films?.mapNotNull { filmNw ->
            Film(name=filmNw.nameRu ?: return@mapNotNull null,
                year = filmNw.year?.toIntOrNull() ?: return@mapNotNull null,
                posterUrl = filmNw.posterUrl ?: "",
                posterUrlPreview =  filmNw.posterUrlPreview ?: "",
                rating = filmNw.rating)
        } ?: emptyList()

}