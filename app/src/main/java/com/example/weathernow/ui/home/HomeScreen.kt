package com.example.weathernow.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathernow.ui.navigation.ForecastUiModel
import com.example.weathernow.ui.navigation.WeatherUiModel
import com.example.weathernow.ui.theme.getWeatherVisuals

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    weather: WeatherUiModel,
    forecast: List<ForecastUiModel>,
    isForecastLoading: Boolean,
    forecastError: String?,
    isFavourite: Boolean,
    useFahrenheit: Boolean,
    onFavouriteClick: () -> Unit,
    onDetailsClick: () -> Unit
) {
    val visuals = getWeatherVisuals(weather.condition)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(visuals.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(22.dp)
        ) {
            HeaderSection(visualLabel = visuals.label)

            Spacer(modifier = Modifier.height(24.dp))

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(20.dp, RoundedCornerShape(36.dp)),
                shape = RoundedCornerShape(36.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column {
                            Text(
                                text = weather.city,
                                fontSize = 34.sp,
                                fontWeight = FontWeight.ExtraBold
                            )

                            Text(
                                text = weather.country,
                                fontSize = 17.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        FilledTonalIconButton(onClick = onFavouriteClick) {
                            Icon(
                                imageVector = if (isFavourite) {
                                    Icons.Default.Favorite
                                } else {
                                    Icons.Default.FavoriteBorder
                                },
                                contentDescription = null
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = weather.temperatureText(useFahrenheit),
                                fontSize = 68.sp,
                                fontWeight = FontWeight.Black
                            )

                            Text(
                                text = weather.condition,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Feels like ${weather.feelsLikeText(useFahrenheit)}",
                                fontSize = 15.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(112.dp)
                                .background(
                                    color = visuals.accent.copy(alpha = 0.18f),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = visuals.emoji,
                                fontSize = 62.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(26.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        PremiumMetricCard(
                            modifier = Modifier.weight(1f),
                            title = "Humidity",
                            value = weather.humidity,
                            icon = Icons.Default.WaterDrop
                        )

                        PremiumMetricCard(
                            modifier = Modifier.weight(1f),
                            title = "Wind",
                            value = weather.wind,
                            icon = Icons.Default.Air
                        )

                        PremiumMetricCard(
                            modifier = Modifier.weight(1f),
                            title = "Pressure",
                            value = weather.pressure,
                            icon = Icons.Default.Speed
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = onDetailsClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.padding(4.dp))

                        Text(
                            text = "View full details",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            ForecastPremiumCard(
                forecast = forecast,
                isForecastLoading = isForecastLoading,
                forecastError = forecastError,
                useFahrenheit = useFahrenheit
            )

            Spacer(modifier = Modifier.height(20.dp))

            TodayOverviewCard(weather = weather)

            Spacer(modifier = Modifier.height(90.dp))
        }
    }
}

@Composable
private fun HeaderSection(
    visualLabel: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Text(
                text = "WeatherNow",
                fontSize = 35.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )

            Text(
                text = "Premium real-time weather dashboard",
                fontSize = 15.sp,
                color = Color.White.copy(alpha = 0.78f)
            )
        }

        Surface(
            shape = RoundedCornerShape(50),
            color = Color.White.copy(alpha = 0.18f)
        ) {
            Text(
                text = visualLabel,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun ForecastPremiumCard(
    forecast: List<ForecastUiModel>,
    isForecastLoading: Boolean,
    forecastError: String?,
    useFahrenheit: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            when {
                isForecastLoading -> {
                    Text(
                        text = "Next hours",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator()

                        Spacer(modifier = Modifier.padding(8.dp))

                        Text(text = "Loading hourly forecast...")
                    }
                }

                forecastError != null -> {
                    Text(
                        text = "Next hours",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = forecastError,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                forecast.isNotEmpty() -> {
                    ForecastSection(
                        forecast = forecast,
                        useFahrenheit = useFahrenheit
                    )
                }

                else -> {
                    Text(
                        text = "Next hours",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Search a city to load forecast data.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun TodayOverviewCard(
    weather: WeatherUiModel
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "Today at a glance",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OverviewItem("Sunrise", weather.sunrise)
                OverviewItem("Sunset", weather.sunset)
                OverviewItem("UV Index", weather.uvIndex)
            }
        }
    }
}

@Composable
private fun PremiumMetricCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: ImageVector
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun OverviewItem(
    title: String,
    value: String
) {
    Column {
        Text(
            text = title,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}