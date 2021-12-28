package com.example.filmsearch.presentation.filmDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmsearch.domain.entity.Film
import com.example.filmsearch.presentation.common.SingleLiveEvent

class FilmDetailViewModel(private val film: Film) : ViewModel() {
    private val _filmState = MutableLiveData<Film>(film)
    val filmState: LiveData<Film> = _filmState

    private val _backAction = SingleLiveEvent<Unit>()
    val backAction: LiveData<Unit> = _backAction

    fun onbackPressed() {
        _backAction.value = Unit
    }
//    init {
//        _filmState.value = film
//    }
}
