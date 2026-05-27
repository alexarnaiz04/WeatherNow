package com.example.weathernow.ui.detail

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
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
import com.example.weathernow.ui.navigation.WeatherUiModel
import com.example.weathernow.ui.theme.getWeatherVisuals

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    weather: WeatherUiModel,
    useFahrenheit: Boolean,
    onBackClick: () -> Unit
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledTonalIconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.padding(6.dp))

                Column {
                    Text(
                        text = "Weather details",
                        fontSize = 29.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )

                    Text(
                        text = "${weather.city}, ${weather.country}",
                        color = Color.White.copy(alpha = 0.78f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(18.dp, RoundedCornerShape(34.dp)),
                shape = RoundedCornerShape(34.dp)
            ) {
                Column(
                    modifier = Modifier.padding(25.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = visuals.accent.copy(alpha = 0.18f)
                            ) {
                                Text(
                                    text = visuals.label,
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                text = weather.temperatureText(useFahrenheit),
                                fontSize = 58.sp,
                                fontWeight = FontWeight.Black
                            )

                            Text(
                                text = weather.condition,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(104.dp)
                                .background(
                                    color = visuals.accent.copy(alpha = 0.20f),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = visuals.emoji,
                                fontSize = 58.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "Advanced information",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(14.dp))

            DetailRow(
                left = DetailData("Feels like", weather.feelsLikeText(useFahrenheit), Icons.Default.Thermostat),
                right = DetailData("Humidity", weather.humidity, Icons.Default.WaterDrop)
            )

            Spacer(modifier = Modifier.height(12.dp))

            DetailRow(
                left = DetailData("Wind speed", weather.wind, Icons.Default.Air),
                right = DetailData("Pressure", weather.pressure, Icons.Default.Compress)
            )

            Spacer(modifier = Modifier.height(12.dp))

            DetailRow(
                left = DetailData("Sunrise", weather.sunrise, Icons.Default.LightMode),
                right = DetailData("Sunset", weather.sunset, Icons.Default.NightsStay)
            )
        }
    }
}

private data class DetailData(
    val title: String,
    val value: String,
    val icon: ImageVector
)

@Composable
private fun DetailRow(
    left: DetailData,
    right: DetailData
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DetailItem(
            modifier = Modifier.weight(1f),
            data = left
        )

        DetailItem(
            modifier = Modifier.weight(1f),
            data = right
        )
    }
}

@Composable
private fun DetailItem(
    modifier: Modifier = Modifier,
    data: DetailData
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Icon(
                imageVector = data.icon,
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = data.title,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = data.value,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}