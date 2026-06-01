package com.example.weathernow.ui.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathernow.ui.navigation.WeatherUiModel

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    history: List<WeatherUiModel>,
    useFahrenheit: Boolean,
    onCityClick: (WeatherUiModel) -> Unit,
    onDeleteItem: (WeatherUiModel) -> Unit,
    onClearHistory: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Search history",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "${history.size} saved searches"
                )
            }

            if (history.isNotEmpty()) {
                OutlinedButton(
                    onClick = onClearHistory,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteSweep,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.padding(3.dp))
                    Text(text = "Clear")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (history.isEmpty()) {
            EmptyHistoryCard()
        } else {
            history.forEachIndexed { index, weather ->
                HistoryItem(
                    weather = weather,
                    useFahrenheit = useFahrenheit,
                    position = index + 1,
                    onClick = { onCityClick(weather) },
                    onDeleteClick = { onDeleteItem(weather) }
                )
            }
        }
    }
}

@Composable
private fun EmptyHistoryCard() {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.History,
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "No recent searches",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Search for a city and it will appear here automatically."
            )
        }
    }
}

@Composable
private fun HistoryItem(
    weather: WeatherUiModel,
    useFahrenheit: Boolean,
    position: Int,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
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
                    text = "${weather.country} · ${weather.temperatureText(useFahrenheit)} · ${weather.condition}"
                )

                Text(
                    text = "Search #$position · Tap to refresh real weather"
                )
            }

            IconButton(
                onClick = onDeleteClick
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete history item"
                )
            }
        }
    }
}