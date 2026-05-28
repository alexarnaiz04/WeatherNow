package com.example.weathernow.data.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore by preferencesDataStore(
    name = "weather_settings"
)

class SettingsDataStore(
    private val context: Context
) {
    private val useFahrenheitKey = booleanPreferencesKey("use_fahrenheit")
    private val showAdvancedDetailsKey = booleanPreferencesKey("show_advanced_details")
    private val autoRefreshWeatherKey = booleanPreferencesKey("auto_refresh_weather")
    private val compactCardsKey = booleanPreferencesKey("compact_cards")

    val useFahrenheit: Flow<Boolean> = context.settingsDataStore.data.map { preferences ->
        preferences[useFahrenheitKey] ?: false
    }

    val showAdvancedDetails: Flow<Boolean> = context.settingsDataStore.data.map { preferences ->
        preferences[showAdvancedDetailsKey] ?: true
    }

    val autoRefreshWeather: Flow<Boolean> = context.settingsDataStore.data.map { preferences ->
        preferences[autoRefreshWeatherKey] ?: true
    }

    val compactCards: Flow<Boolean> = context.settingsDataStore.data.map { preferences ->
        preferences[compactCardsKey] ?: false
    }

    suspend fun saveUseFahrenheit(value: Boolean) {
        context.settingsDataStore.edit { preferences ->
            preferences[useFahrenheitKey] = value
        }
    }

    suspend fun saveShowAdvancedDetails(value: Boolean) {
        context.settingsDataStore.edit { preferences ->
            preferences[showAdvancedDetailsKey] = value
        }
    }

    suspend fun saveAutoRefreshWeather(value: Boolean) {
        context.settingsDataStore.edit { preferences ->
            preferences[autoRefreshWeatherKey] = value
        }
    }

    suspend fun saveCompactCards(value: Boolean) {
        context.settingsDataStore.edit { preferences ->
            preferences[compactCardsKey] = value
        }
    }
}