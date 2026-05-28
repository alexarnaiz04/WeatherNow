package com.example.weathernow.ui.favourites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathernow.ui.navigation.WeatherUiModel
import com.example.weathernow.ui.theme.getWeatherVisuals

@Composable
fun FavouritesScreen(
    modifier: Modifier = Modifier,
    favourites: List<WeatherUiModel>,
    useFahrenheit: Boolean,
    compactCards: Boolean,
    onCityClick: (WeatherUiModel) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Favourite locations",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Tap a favourite city to refresh its live weather."
        )

        Spacer(modifier = Modifier.height(22.dp))

        if (favourites.isEmpty()) {
            EmptyFavouritesCard()
        } else {
            favourites.forEach { weather ->
                FavouriteCityCard(
                    weather = weather,
                    useFahrenheit = useFahrenheit,
                    compactCards = compactCards,
                    onClick = { onCityClick(weather) }
                )
            }
        }

        Spacer(modifier = Modifier.height(90.dp))
    }
}

@Composable
private fun EmptyFavouritesCard() {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "💙",
                fontSize = 42.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "No favourite locations yet",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Add cities from the Home screen and they will appear here."
            )
        }
    }
}

@Composable
private fun FavouriteCityCard(
    weather: WeatherUiModel,
    useFahrenheit: Boolean,
    compactCards: Boolean,
    onClick: () -> Unit
) {
    val visuals = getWeatherVisuals(weather.condition)
    val cardPadding = if (compactCards) 16.dp else 22.dp
    val emojiSize = if (compactCards) 38.sp else 52.sp
    val citySize = if (compactCards) 20.sp else 24.sp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = if (compactCards) 10.dp else 16.dp)
            .shadow(12.dp, RoundedCornerShape(30.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(30.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(
                            visuals.accent.copy(alpha = 0.95f),
                            visuals.accent.copy(alpha = 0.45f),
                            Color.White.copy(alpha = 0.20f)
                        )
                    )
                )
                .padding(cardPadding)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color.White
                        )

                        Spacer(modifier = Modifier.padding(4.dp))

                        Text(
                            text = "Saved city",
                            color = Color.White.copy(alpha = 0.88f),
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = weather.city,
                        fontSize = citySize,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )

                    Text(
                        text = weather.country,
                        color = Color.White.copy(alpha = 0.86f)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${weather.temperatureText(useFahrenheit)} · ${weather.condition}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    if (!compactCards) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Humidity ${weather.humidity} · Wind ${weather.wind}",
                            color = Color.White.copy(alpha = 0.88f)
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                Color.White.copy(alpha = 0.22f),
                                CircleShape
                            )
                            .padding(14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = visuals.emoji,
                            fontSize = emojiSize
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        }
    }
}