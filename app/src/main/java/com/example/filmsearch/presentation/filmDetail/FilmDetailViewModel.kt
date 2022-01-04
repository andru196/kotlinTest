package com.example.filmsearch.presentation.filmDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmsearch.data.local.FavoritesDao
import com.example.filmsearch.domain.FilmRepository
import com.example.filmsearch.domain.entity.Film
import com.example.filmsearch.presentation.common.SingleLiveEvent
import com.example.filmsearch.presentation.common.launchWithErrorHandler
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import javax.inject.Inject

class FilmDetailViewModel @AssistedInject constructor(
    @Assisted private val film: Film,
    private val filmRepository: FilmRepository,
    private val favoritesDao: FavoritesDao
    ) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(film: Film) : FilmDetailViewModel
    }
    private val _filmState = MutableLiveData<Film>(film)
    val filmState: LiveData<Film> = _filmState

    private val _backAction = SingleLiveEvent<Unit>()
    val backAction: LiveData<Unit> = _backAction

    private val _favoriteState = MutableLiveData<Boolean>()
    val favoriteState: LiveData<Boolean> = _favoriteState

    fun onbackPressed() {
        _backAction.value = Unit
    }

    init {
        viewModelScope.launch {
            _favoriteState.value = favoritesDao.isInFavorites(film)
        }
    }


    fun onFavoritesClicked() {
        viewModelScope.launchWithErrorHandler {
            val isInFavorites = favoritesDao.isInFavorites(film)
            _favoriteState.value = !isInFavorites
            if (isInFavorites)
                favoritesDao.delete(film)
            else
                favoritesDao.add(film)
        }
    }
}