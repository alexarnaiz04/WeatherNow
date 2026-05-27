package com.example.weathernow.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathernow.ui.navigation.WeatherUiModel
import com.example.weathernow.ui.navigation.getWeatherStyle

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    weather: WeatherUiModel,
    isFavourite: Boolean,
    onFavouriteClick: () -> Unit,
    onDetailsClick: () -> Unit
) {
    val style = getWeatherStyle(weather.condition)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(style.backgroundTop, style.backgroundBottom)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column {
                    Text(
                        text = "WeatherNow",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null
                        )
                        Text(
                            text = "${weather.city}, ${weather.country}",
                            fontSize = 17.sp
                        )
                    }
                }

                IconButton(onClick = onFavouriteClick) {
                    Icon(
                        imageVector = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SuggestionChip(
                    onClick = {},
                    label = { Text("Live weather") }
                )
                SuggestionChip(
                    onClick = {},
                    label = { Text("Forecast") }
                )
                SuggestionChip(
                    onClick = {},
                    label = { Text("Air quality") }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(36.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 14.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(26.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = style.icon,
                        contentDescription = null,
                        modifier = Modifier.height(110.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = weather.temperature,
                        fontSize = 70.sp,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Text(
                        text = weather.condition,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Feels like ${weather.feelsLike}",
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    ElevatedButton(
                        onClick = onDetailsClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Text(
                            text = "View detailed weather",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            WeatherInfoGrid(weather)

            Spacer(modifier = Modifier.height(22.dp))

            HourlyForecast()

            Spacer(modifier = Modifier.height(22.dp))

            WeeklyForecast()

            Spacer(modifier = Modifier.height(22.dp))

            WeatherRecommendation(weather)

            Spacer(modifier = Modifier.height(22.dp))

            Button(
                onClick = onFavouriteClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(18.dp)
            ) {
                Text(
                    text = if (isFavourite) "Remove from favourites" else "Add to favourites",
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun WeatherInfoGrid(weather: WeatherUiModel) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Current conditions",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WeatherMiniCard(
                modifier = Modifier.weight(1f),
                title = "Humidity",
                value = weather.humidity,
                icon = Icons.Default.WaterDrop
            )

            WeatherMiniCard(
                modifier = Modifier.weight(1f),
                title = "Wind",
                value = weather.wind,
                icon = Icons.Default.Air
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            WeatherMiniCard(
                modifier = Modifier.weight(1f),
                title = "Pressure",
                value = weather.pressure,
                icon = Icons.Default.Speed
            )

            WeatherMiniCard(
                modifier = Modifier.weight(1f),
                title = "UV Index",
                value = weather.uvIndex,
                icon = Icons.Default.WbSunny
            )
        }
    }
}

@Composable
private fun WeatherMiniCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                fontSize = 13.sp
            )

            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun HourlyForecast() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Hourly forecast",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            HourCard("09:00", "15°C")
            HourCard("12:00", "18°C")
            HourCard("15:00", "20°C")
            HourCard("18:00", "17°C")
            HourCard("21:00", "14°C")
        }
    }
}

@Composable
private fun HourCard(
    hour: String,
    temperature: String
) {
    Card(
        shape = RoundedCornerShape(22.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = hour)
            Spacer(modifier = Modifier.height(8.dp))
            Icon(
                imageVector = Icons.Default.Cloud,
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = temperature,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun WeeklyForecast() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Weekly forecast",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        ForecastRow("Monday", "18°C", "Cloudy")
        ForecastRow("Tuesday", "21°C", "Sunny")
        ForecastRow("Wednesday", "19°C", "Partly cloudy")
        ForecastRow("Thursday", "17°C", "Rainy")
        ForecastRow("Friday", "22°C", "Clear sky")
    }
}

@Composable
private fun ForecastRow(
    day: String,
    temperature: String,
    condition: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = day, fontWeight = FontWeight.Bold)
            Text(text = condition)
            Text(text = temperature, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun WeatherRecommendation(weather: WeatherUiModel) {
    val recommendation = when {
        weather.condition.contains("rain", ignoreCase = true) ->
            "Take an umbrella before going outside."

        weather.condition.contains("sun", ignoreCase = true) ||
                weather.condition.contains("clear", ignoreCase = true) ->
            "Good day for outdoor activities."

        else ->
            "Weather is stable. A light jacket may be useful."
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.DeviceThermostat,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.padding(6.dp))

                Text(
                    text = "Smart recommendation",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = recommendation)
        }
    }
}