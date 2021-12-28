package com.example.filmsearch.presentation.topFilms

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmsearch.domain.FilmRepository
import com.example.filmsearch.domain.entity.Film
import com.example.filmsearch.domain.entity.TopType
import com.example.filmsearch.presentation.common.launchWithErrorHandler
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

class TopFilmsViewModel(
    private val filmRepository: FilmRepository
): ViewModel() {

    private val _filmsState = MutableLiveData<List<Film>>()
    val filmState: LiveData<List<Film>> = _filmsState
    init {
        viewModelScope.launchWithErrorHandler {
            val films = filmRepository.getTopFilms(TopType.TOP_AWAIT_FILMS, 1)
            _filmsState.value = films
        }
    }
    private val _countState = MutableLiveData<Int>(0)
    val countState: LiveData<Int> = _countState

    fun onAdd() {
        _countState.value = _countState.value!! + 1
    }
}