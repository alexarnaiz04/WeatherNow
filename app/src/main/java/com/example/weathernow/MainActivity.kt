package com.example.weathernow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.weathernow.ui.navigation.AppNavGraph
import com.example.weathernow.ui.theme.WeatherNowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WeatherNowTheme {
                AppNavGraph()
            }
        }
    }
}