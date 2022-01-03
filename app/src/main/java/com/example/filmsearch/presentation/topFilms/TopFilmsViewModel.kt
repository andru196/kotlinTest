package com.example.filmsearch.presentation.topFilms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmsearch.domain.FilmRepository
import com.example.filmsearch.domain.entity.Film
import com.example.filmsearch.domain.entity.TopType
import com.example.filmsearch.presentation.common.SingleLiveEvent
import com.example.filmsearch.presentation.common.launchWithErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopFilmsViewModel @Inject constructor(
    private val filmRepository: FilmRepository
): ViewModel() {

    private val _screenState = MutableLiveData<TopFilmsState>(TopFilmsState.Loading())
    val screenState: LiveData<TopFilmsState> = _screenState

    private val _openDetailAction = SingleLiveEvent<Film>()
    val openDetailAction: LiveData<Film> = _openDetailAction

    init {
        viewModelScope.launchWithErrorHandler(block =  {
            val films = filmRepository.getTopFilms(TopType.TOP_AWAIT_FILMS, 1)
            _screenState.value = TopFilmsState.Success(films)
        }, onError = {
            _screenState.value = TopFilmsState.Error(it)
        })
    }

    fun onFilmClicked(film: Film) {
        _openDetailAction.value = film
    }
}

sealed class TopFilmsState {
    class Loading(): TopFilmsState()
    class Success(val films: List<Film>): TopFilmsState()
    class Error(val throwable: Throwable) : TopFilmsState()
}