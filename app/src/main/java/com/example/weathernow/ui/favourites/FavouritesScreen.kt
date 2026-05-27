package com.example.weathernow.ui.favourites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathernow.ui.navigation.WeatherUiModel

@Composable
fun FavouritesScreen(
    modifier: Modifier = Modifier,
    favourites: List<WeatherUiModel>,
    onCityClick: (WeatherUiModel) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Favourite locations",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (favourites.isEmpty()) {
            EmptyFavouritesCard()
        } else {
            favourites.forEach { weather ->
                FavouriteCityCard(
                    weather = weather,
                    onClick = {
                        onCityClick(weather)
                    }
                )
            }
        }
    }
}

@Composable
private fun EmptyFavouritesCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {

            Text(
                text = "No favourite locations yet",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Add cities from the Home screen to keep them here."
            )
        }
    }
}

@Composable
private fun FavouriteCityCard(
    weather: WeatherUiModel,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(22.dp)
    ) {

        Column(
            modifier = Modifier.padding(18.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = weather.city,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Text(text = weather.country)

            Text(
                text = "${weather.temperatureCelsius} · ${weather.condition}"
            )
        }
    }
}