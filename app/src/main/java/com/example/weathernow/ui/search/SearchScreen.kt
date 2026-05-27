package com.example.weathernow.ui.search

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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathernow.ui.navigation.WeatherUiModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    availableCities: List<WeatherUiModel>,
    useFahrenheit: Boolean,
    onSearch: (WeatherUiModel) -> Unit
) {
    var city by remember { mutableStateOf("") }

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
            text = "Find weather conditions for your next destination."
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
                    onValueChange = { city = it },
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
                        val result = availableCities.firstOrNull {
                            it.city.equals(city, ignoreCase = true)
                        } ?: WeatherUiModel(
                            city = if (city.isBlank()) "Unknown city" else city,
                            country = "Unknown",
                            temperatureCelsius = 21,
                            condition = "Estimated weather",
                            humidity = "50%",
                            wind = "10 km/h",
                            feelsLikeCelsius = 20
                        )

                        onSearch(result)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text(
                        text = "Search weather",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "Popular cities",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row {
            PopularChip("Madrid", availableCities, onSearch)
            Spacer(modifier = Modifier.padding(4.dp))
            PopularChip("London", availableCities, onSearch)
            Spacer(modifier = Modifier.padding(4.dp))
            PopularChip("Paris", availableCities, onSearch)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Suggested results",
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
    availableCities: List<WeatherUiModel>,
    onSearch: (WeatherUiModel) -> Unit
) {
    val weather = availableCities.first { it.city == city }

    SuggestionChip(
        onClick = { onSearch(weather) },
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