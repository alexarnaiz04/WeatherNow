package com.example.weathernow.ui.history

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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    history: SnapshotStateList<String>
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
            history.forEachIndexed { index, city ->
                HistoryItem(
                    city = city,
                    position = index + 1
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
    city: String,
    position: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp),
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
                text = city,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            Text(text = "Recent search #$position")
        }
    }
}