package com.example.weathernow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.weathernow.ui.navigation.AppNavGraph
import com.example.weathernow.ui.theme.WeatherNowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WeatherNowTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppNavGraph()
                }
            }
        }
    }
}