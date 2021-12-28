package com.example.filmsearch.presentation.topFilms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TopFilmsViewModel: ViewModel() {
    private val _countState = MutableLiveData<Int>(0)
    val countState: LiveData<Int> = _countState

    fun onAdd() {
        _countState.value = _countState.value!! + 1
    }
}