package com.example.weathernow.ui.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weathernow.ui.navigation.ForecastUiModel

@Composable
fun ForecastSection(
    forecast: List<ForecastUiModel>,
    useFahrenheit: Boolean
) {

    Column {

        Text(
            text = "Next hours",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            forecast.forEach { item ->

                ForecastCard(
                    forecast = item,
                    useFahrenheit = useFahrenheit
                )
            }
        }
    }
}

@Composable
private fun ForecastCard(
    forecast: ForecastUiModel,
    useFahrenheit: Boolean
) {

    val temperature = if (useFahrenheit) {
        forecast.temperature * 9 / 5 + 32
    } else {
        forecast.temperature
    }

    val unit = if (useFahrenheit) "°F" else "°C"

    Card(
        shape = RoundedCornerShape(24.dp)
    ) {

        Column(
            modifier = Modifier.padding(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = forecast.time,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Text(
                text = weatherEmoji(forecast.condition),
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.padding(4.dp))

            Text(
                text = "$temperature$unit",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = forecast.condition,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

private fun weatherEmoji(condition: String): String {

    return when {
        condition.contains("Rain", true) -> "🌧"
        condition.contains("Cloud", true) -> "☁"
        condition.contains("Clear", true) -> "☀"
        condition.contains("Snow", true) -> "❄"
        else -> "🌤"
    }
}