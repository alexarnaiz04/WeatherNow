package com.example.weathernow.ui.search

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathernow.data.location.LocationProvider
import com.example.weathernow.data.mapper.toUiModel
import com.example.weathernow.data.remote.WeatherRemoteDataSource
import com.example.weathernow.ui.navigation.WeatherUiModel
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    availableCities: List<WeatherUiModel>,
    useFahrenheit: Boolean,
    onSearch: (WeatherUiModel) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val remoteDataSource = remember { WeatherRemoteDataSource() }
    val locationProvider = remember { LocationProvider(context) }

    var city by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    fun loadWeatherByCurrentLocation() {
        coroutineScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val location = locationProvider.getCurrentLocation()

                if (location == null) {
                    errorMessage = "Location unavailable. Make sure GPS is enabled and try again."
                    return@launch
                }

                val response = remoteDataSource.getWeatherByCoordinates(
                    latitude = location.latitude,
                    longitude = location.longitude
                )

                onSearch(response.toUiModel())
            } catch (e: Exception) {
                errorMessage = "Could not load weather from your location."
            } finally {
                isLoading = false
            }
        }
    }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (fineGranted || coarseGranted) {
            loadWeatherByCurrentLocation()
        } else {
            errorMessage = "Location permission denied."
        }
    }

    val filteredSuggestions = availableCities.filter {
        it.city.contains(city, ignoreCase = true)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Search city",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Find weather by city name or by your current location."
        )

        Spacer(modifier = Modifier.height(20.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.TravelExplore,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = city,
                    onValueChange = {
                        city = it
                        errorMessage = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("City name") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    },
                    shape = RoundedCornerShape(18.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (city.isBlank()) {
                            errorMessage = "Please enter a city name."
                            return@Button
                        }

                        coroutineScope.launch {
                            isLoading = true
                            errorMessage = null

                            try {
                                val response = remoteDataSource.searchWeather(city.trim())
                                onSearch(response.toUiModel())
                            } catch (e: Exception) {
                                errorMessage = "City not found or internet connection failed."
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(18.dp),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator()
                    } else {
                        Text(
                            text = "Search weather",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = {
                        if (locationProvider.hasLocationPermission()) {
                            loadWeatherByCurrentLocation()
                        } else {
                            locationPermissionLauncher.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(18.dp),
                    enabled = !isLoading
                ) {
                    Icon(
                        imageVector = Icons.Default.MyLocation,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.padding(4.dp))

                    Text(
                        text = "Use my current location",
                        fontWeight = FontWeight.Bold
                    )
                }

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = errorMessage!!)
                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "Quick examples",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row {
            PopularChip("Madrid", onCitySelected = { city = it })
            Spacer(modifier = Modifier.padding(4.dp))
            PopularChip("London", onCitySelected = { city = it })
            Spacer(modifier = Modifier.padding(4.dp))
            PopularChip("Paris", onCitySelected = { city = it })
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Previous demo cities",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        filteredSuggestions.forEach { weather ->
            SearchResultCard(
                weather = weather,
                useFahrenheit = useFahrenheit,
                onClick = { onSearch(weather) }
            )
        }
    }
}

@Composable
private fun PopularChip(
    city: String,
    onCitySelected: (String) -> Unit
) {
    SuggestionChip(
        onClick = { onCitySelected(city) },
        label = { Text(city) }
    )
}

@Composable
private fun SearchResultCard(
    weather: WeatherUiModel,
    useFahrenheit: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Icon(
                imageVector = Icons.Default.LocationCity,
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${weather.city}, ${weather.country}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "${weather.temperatureText(useFahrenheit)} · ${weather.condition}")
            Text(text = "Humidity: ${weather.humidity} · Wind: ${weather.wind}")
        }
    }
}