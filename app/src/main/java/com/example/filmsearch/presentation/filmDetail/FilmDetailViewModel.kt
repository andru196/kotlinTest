package com.example.filmsearch.presentation.filmDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmsearch.domain.FilmRepository
import com.example.filmsearch.domain.entity.Film
import com.example.filmsearch.presentation.common.SingleLiveEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import javax.inject.Inject

class FilmDetailViewModel @AssistedInject constructor(
    @Assisted private val film: Film,
    private val filmRepository: FilmRepository
    ) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(film: Film) : FilmDetailViewModel
    }
    private val _filmState = MutableLiveData<Film>(film)

    val filmState: LiveData<Film> = _filmState

    private val _backAction = SingleLiveEvent<Unit>()
    val backAction: LiveData<Unit> = _backAction

    fun onbackPressed() {
        _backAction.value = Unit
    }
}

class FilmDetailViewModelFactory @Inject constructor(
    private  val filmRepository: FilmRepository
    ) {
    fun create(film: Film): FilmDetailViewModel = FilmDetailViewModel(film, filmRepository)
}