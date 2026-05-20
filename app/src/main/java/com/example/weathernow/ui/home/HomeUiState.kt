package com.example.weathernow.ui.home

import com.example.weathernow.domain.model.CurrentWeather
import com.example.weathernow.domain.model.ForecastItem

data class HomeUiState(
    val isLoading: Boolean = false,
    val currentWeather: CurrentWeather? = null,
    val forecast: List<ForecastItem> = emptyList(),
    val errorMessage: String? = null,
    val selectedCity: String = "Szczecin"
)