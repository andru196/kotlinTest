package com.example.filmsearch.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmsearch.data.local.FavoritesDao
import com.example.filmsearch.presentation.common.launchWithErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val favoritesDao: FavoritesDao
) : ViewModel() {
    private val _favoritesCountState = MutableLiveData<Int>()
    val favoritesCountState: LiveData<Int> = _favoritesCountState

    init {
        viewModelScope.launchWithErrorHandler {
            favoritesDao.getCount().collectLatest {
                _favoritesCountState.value = it
            }
        }
    }
}