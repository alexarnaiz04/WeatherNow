package com.example.weathernow.ui.project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.DataObject
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Web
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProjectInfoScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Project Info",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "WeatherNow is a medium-level Android weather application built with Clean Architecture."
        )

        Spacer(modifier = Modifier.height(20.dp))

        InfoCard(
            title = "Architecture",
            description = "The project is divided into ui, domain, data and di layers.",
            icon = Icons.Default.Code
        )

        InfoCard(
            title = "Remote API",
            description = "Retrofit service contracts are prepared for weather and forecast endpoints.",
            icon = Icons.Default.Web
        )

        InfoCard(
            title = "Local Database",
            description = "Room entities and DAO interfaces are prepared for favourites and search history.",
            icon = Icons.Default.Storage
        )

        InfoCard(
            title = "Data Models",
            description = "The domain layer contains clean Kotlin models used across the app.",
            icon = Icons.Default.DataObject
        )
    }
}

@Composable
private fun InfoCard(
    title: String,
    description: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(text = description)
        }
    }
}