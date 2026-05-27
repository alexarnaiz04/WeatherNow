package com.example.weathernow.ui.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathernow.ui.navigation.WeatherUiModel

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    history: List<WeatherUiModel>,
    onCityClick: (WeatherUiModel) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Search history",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (history.isEmpty()) {

            EmptyHistoryCard()

        } else {

            history.forEachIndexed { index, weather ->

                HistoryItem(
                    weather = weather,
                    position = index + 1,
                    onClick = {
                        onCityClick(weather)
                    }
                )
            }
        }
    }
}

@Composable
private fun EmptyHistoryCard() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp)
    ) {

        Column(
            modifier = Modifier.padding(24.dp)
        ) {

            Text(
                text = "No recent searches",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Search for a city to build your weather history."
            )
        }
    }
}

@Composable
private fun HistoryItem(
    weather: WeatherUiModel,
    position: Int,
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
            modifier = Modifier.padding(18.dp)
        ) {

            Icon(
                imageVector = Icons.Default.History,
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = weather.city,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Text(
                text = "${weather.country} · ${weather.temperatureCelsius} · ${weather.condition}"
            )

            Text(
                text = "Recent search #$position"
            )
        }
    }
}