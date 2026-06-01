package com.example.weathernow.ui.settings

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
import androidx.compose.material3.Icon
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
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Settings",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Customize how WeatherNow works and displays live weather data."
        )

        Spacer(modifier = Modifier.height(24.dp))

        SettingSwitchCard(
            title = "Temperature unit",
            subtitle = if (useFahrenheit) "Using Fahrenheit" else "Using Celsius",
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
                "Humidity, wind, pressure and sunrise data enabled"
            } else {
                "Showing a cleaner simplified view"
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
            title = "Auto refresh weather",
            subtitle = if (autoRefreshWeather) {
                "Saved cities refresh from OpenWeather when opened"
            } else {
                "Saved cities keep their stored offline values"
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
                "More compact lists in the app"
            } else {
                "Premium spacious cards enabled"
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

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Current configuration",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "WeatherNow is connected to OpenWeather and uses live data for searches, favourites and history."
                )
            }
        }
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
        shape = RoundedCornerShape(24.dp)
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
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(text = subtitle)
                }
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}