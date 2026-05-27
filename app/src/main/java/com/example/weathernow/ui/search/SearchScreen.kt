package com.example.weathernow.ui.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
    onSearch: (WeatherUiModel) -> Unit
) {
    var city by remember { mutableStateOf("") }

    val suggestions = listOf(
        WeatherUiModel("Szczecin", "Poland", "18°C", "Partly cloudy", "64%", "12 km/h", "17°C"),
        WeatherUiModel("Madrid", "Spain", "24°C", "Sunny", "40%", "8 km/h", "25°C"),
        WeatherUiModel("London", "United Kingdom", "14°C", "Rainy", "78%", "16 km/h", "13°C"),
        WeatherUiModel("Berlin", "Germany", "16°C", "Cloudy", "60%", "10 km/h", "15°C"),
        WeatherUiModel("Paris", "France", "20°C", "Clear sky", "55%", "9 km/h", "21°C")
    )

    val filteredSuggestions = suggestions.filter {
        it.city.contains(city, ignoreCase = true)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Search city",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

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
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val result = suggestions.firstOrNull {
                    it.city.equals(city, ignoreCase = true)
                } ?: WeatherUiModel(
                    city = if (city.isBlank()) "Unknown city" else city,
                    country = "Unknown",
                    temperature = "21°C",
                    condition = "Estimated weather",
                    humidity = "50%",
                    wind = "10 km/h",
                    feelsLike = "20°C"
                )

                onSearch(result)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search weather")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Suggested cities",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        filteredSuggestions.forEach { weather ->
            SearchResultCard(
                weather = weather,
                onClick = { onSearch(weather) }
            )
        }
    }
}

@Composable
private fun SearchResultCard(
    weather: WeatherUiModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = "${weather.city}, ${weather.country}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "${weather.temperature} · ${weather.condition}")
            Text(text = "Humidity: ${weather.humidity} · Wind: ${weather.wind}")
        }
    }
}