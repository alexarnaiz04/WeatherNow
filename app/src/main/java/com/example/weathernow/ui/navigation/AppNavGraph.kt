package com.example.weathernow.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.weathernow.ui.detail.DetailScreen
import com.example.weathernow.ui.favourites.FavouritesScreen
import com.example.weathernow.ui.history.HistoryScreen
import com.example.weathernow.ui.home.HomeScreen
import com.example.weathernow.ui.project.ProjectInfoScreen
import com.example.weathernow.ui.search.SearchScreen

@Composable
fun AppNavGraph() {
    var selectedTab by remember { mutableIntStateOf(0) }
    var showDetails by remember { mutableStateOf(false) }

    var currentWeather by remember {
        mutableStateOf(
            WeatherUiModel(
                city = "Szczecin",
                country = "Poland",
                temperature = "18°C",
                condition = "Partly cloudy",
                humidity = "64%",
                wind = "12 km/h",
                feelsLike = "17°C"
            )
        )
    }

    val favourites = remember {
        mutableStateListOf(
            WeatherUiModel("Szczecin", "Poland", "18°C", "Partly cloudy", "64%", "12 km/h", "17°C"),
            WeatherUiModel("Madrid", "Spain", "24°C", "Sunny", "40%", "8 km/h", "25°C")
        )
    }

    val history = remember {
        mutableStateListOf(
            "Szczecin",
            "Madrid",
            "Berlin"
        )
    }

    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home),
        BottomNavItem("Search", Icons.Default.Search),
        BottomNavItem("Favs", Icons.Default.Favorite),
        BottomNavItem("History", Icons.Default.History),
        BottomNavItem("Project", Icons.Default.Info)
    )

    Scaffold(
        bottomBar = {
            if (!showDetails) {
                NavigationBar {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedTab == index,
                            onClick = {
                                selectedTab = index
                                showDetails = false
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label
                                )
                            },
                            label = {
                                Text(text = item.label)
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        if (showDetails) {
            DetailScreen(
                modifier = Modifier.padding(paddingValues),
                weather = currentWeather,
                onBackClick = { showDetails = false }
            )
        } else {
            when (selectedTab) {
                0 -> HomeScreen(
                    modifier = Modifier.padding(paddingValues),
                    weather = currentWeather,
                    isFavourite = favourites.any { it.city == currentWeather.city },
                    onFavouriteClick = {
                        if (favourites.any { it.city == currentWeather.city }) {
                            favourites.removeAll { it.city == currentWeather.city }
                        } else {
                            favourites.add(currentWeather)
                        }
                    },
                    onDetailsClick = {
                        showDetails = true
                    }
                )

                1 -> SearchScreen(
                    modifier = Modifier.padding(paddingValues),
                    onSearch = { selectedWeather ->
                        currentWeather = selectedWeather
                        history.remove(selectedWeather.city)
                        history.add(0, selectedWeather.city)
                        selectedTab = 0
                    }
                )

                2 -> FavouritesScreen(
                    modifier = Modifier.padding(paddingValues),
                    favourites = favourites,
                    onCityClick = { selectedWeather ->
                        currentWeather = selectedWeather
                        selectedTab = 0
                    }
                )

                3 -> HistoryScreen(
                    modifier = Modifier.padding(paddingValues),
                    history = history
                )

                4 -> ProjectInfoScreen(
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}