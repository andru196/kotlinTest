package com.example.filmsearch.presentation.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmsearch.data.network.GeoApi
import com.example.filmsearch.domain.GeoRepository
import com.example.filmsearch.domain.entity.City
import com.example.filmsearch.presentation.common.launchWithErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
    private val geoRepository: GeoRepository
) : ViewModel() {
    private val _cityState = MutableLiveData<City>()
    val cityState: LiveData<City> = _cityState

    fun onLocationResult(latitude: Double, longitude: Double) {
        viewModelScope.launchWithErrorHandler {
            val city = geoRepository.getCity(latitude, longitude)
            _cityState.value = city
        }
    }
}