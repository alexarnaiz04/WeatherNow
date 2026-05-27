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

    val useFahrenheit: Flow<Boolean> = context.settingsDataStore.data.map { preferences ->
        preferences[useFahrenheitKey] ?: false
    }

    suspend fun saveUseFahrenheit(value: Boolean) {
        context.settingsDataStore.edit { preferences ->
            preferences[useFahrenheitKey] = value
        }
    }
}