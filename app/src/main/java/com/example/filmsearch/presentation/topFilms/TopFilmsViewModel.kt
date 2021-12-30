package com.example.filmsearch.presentation.topFilms

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmsearch.domain.FilmRepository
import com.example.filmsearch.domain.entity.Film
import com.example.filmsearch.domain.entity.TopType
import com.example.filmsearch.presentation.common.SingleLiveEvent
import com.example.filmsearch.presentation.common.launchWithErrorHandler
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopFilmsViewModel @Inject constructor(
    private val filmRepository: FilmRepository
): ViewModel() {

    private val _filmsState = MutableLiveData<List<Film>>()
    val filmState: LiveData<List<Film>> = _filmsState

    private val _openDetailAction = SingleLiveEvent<Film>()
    val openDetailAction: LiveData<Film> = _openDetailAction

    init {
        viewModelScope.launchWithErrorHandler {
            val films = filmRepository.getTopFilms(TopType.TOP_AWAIT_FILMS, 1)
            _filmsState.value = films
        }
    }

    fun onFilmClicked(film: Film) {
        _openDetailAction.value = film
    }
}