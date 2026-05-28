package com.example.weathernow.ui.settings

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.ViewCompact
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    useFahrenheit: Boolean,
    showAdvancedDetails: Boolean,
    autoRefreshWeather: Boolean,
    compactCards: Boolean,
    onUnitChange: (Boolean) -> Unit,
    onShowAdvancedDetailsChange: (Boolean) -> Unit,
    onAutoRefreshWeatherChange: (Boolean) -> Unit,
    onCompactCardsChange: (Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Settings",
            fontSize = 34.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Customize how WeatherNow looks and behaves."
        )

        Spacer(modifier = Modifier.height(24.dp))

        SettingSwitchCard(
            title = "Temperature unit",
            subtitle = if (useFahrenheit) "Temperatures are shown in Fahrenheit." else "Temperatures are shown in Celsius.",
            checked = useFahrenheit,
            onCheckedChange = onUnitChange,
            icon = {
                Icon(
                    imageVector = Icons.Default.Thermostat,
                    contentDescription = null
                )
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        SettingSwitchCard(
            title = "Advanced details",
            subtitle = if (showAdvancedDetails) {
                "Home shows pressure, sunrise and sunset."
            } else {
                "Home uses a cleaner simplified layout."
            },
            checked = showAdvancedDetails,
            onCheckedChange = onShowAdvancedDetailsChange,
            icon = {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = null
                )
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        SettingSwitchCard(
            title = "Auto refresh saved cities",
            subtitle = if (autoRefreshWeather) {
                "Favourites and history refresh with live OpenWeather data."
            } else {
                "Favourites and history open stored offline data."
            },
            checked = autoRefreshWeather,
            onCheckedChange = onAutoRefreshWeatherChange,
            icon = {
                Icon(
                    imageVector = Icons.Default.CloudSync,
                    contentDescription = null
                )
            }
        )

        Spacer(modifier = Modifier.height(14.dp))

        SettingSwitchCard(
            title = "Compact cards",
            subtitle = if (compactCards) {
                "Lists use smaller cards with less detail."
            } else {
                "Lists use larger premium visual cards."
            },
            checked = compactCards,
            onCheckedChange = onCompactCardsChange,
            icon = {
                Icon(
                    imageVector = Icons.Default.ViewCompact,
                    contentDescription = null
                )
            }
        )

        Spacer(modifier = Modifier.height(22.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp)
        ) {
            Column(
                modifier = Modifier.padding(22.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Live weather enabled",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "WeatherNow is connected to OpenWeather. Searches, favourites and history can load real-time weather data."
                )
            }
        }

        Spacer(modifier = Modifier.height(90.dp))
    }
}

@Composable
private fun SettingSwitchCard(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    icon: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                icon()

                Spacer(modifier = Modifier.padding(8.dp))

                Column {
                    Text(
                        text = title,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = subtitle,
                        fontSize = 14.sp
                    )
                }
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}