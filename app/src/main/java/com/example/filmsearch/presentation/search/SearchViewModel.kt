package com.example.filmsearch.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmsearch.domain.FilmRepository
import com.example.filmsearch.presentation.common.launchWithErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val filmRepository: FilmRepository
) : ViewModel() {

    private var searchJob: Job? = null

    private val _yearFromErrorState = MutableLiveData<YearState>(YearState.VALID)
    val yearFromState: LiveData<YearState> = _yearFromErrorState

    private val _yearToErrorState = MutableLiveData<YearState>(YearState.VALID)
    val yearToState: LiveData<YearState> = _yearToErrorState

    private val _loadingState = MutableLiveData<Boolean>(false)
    val loadingState: LiveData<Boolean> = _loadingState

    fun search(yearFrom: String, yearTo: String) {
        var hasError = false
        if (yearFrom.isBlank()) {
            _yearFromErrorState.value = YearState.EMPTY
            hasError = true
        }
        if (yearTo.isBlank()) {
            _yearToErrorState.value = YearState.EMPTY
            hasError = true
        }

        if (hasError) return

        _yearFromErrorState.value = YearState.VALID
        _yearToErrorState.value = YearState.VALID

        _loadingState.value = true

        if (searchJob?.isActive == true)
            searchJob?.cancel()
        searchJob = viewModelScope.launchWithErrorHandler ({
            _loadingState.value = false
        }, {
            filmRepository.search(yearFrom.toIntOrNull(), yearTo.toIntOrNull())
            _loadingState.value = false
        })
    }

    fun onTearFromChange(yearFrom:String) {
        if (yearFrom.length == 4)
            _yearFromErrorState.value = YearState.VALID
    }
}

enum class YearState {
    EMPTY, VALID
}
