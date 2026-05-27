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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.weathernow.data.local.WeatherDatabase
import com.example.weathernow.data.mapper.toFavouriteEntity
import com.example.weathernow.data.mapper.toHistoryEntity
import com.example.weathernow.data.mapper.toUiModel
import com.example.weathernow.ui.detail.DetailScreen
import com.example.weathernow.ui.favourites.FavouritesScreen
import com.example.weathernow.ui.history.HistoryScreen
import com.example.weathernow.ui.home.HomeScreen
import com.example.weathernow.ui.project.ProjectInfoScreen
import com.example.weathernow.ui.search.SearchScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavGraph() {
    val context = LocalContext.current
    val database = remember { WeatherDatabase.getDatabase(context) }
    val weatherDao = remember { database.weatherDao() }
    val coroutineScope = rememberCoroutineScope()

    val favouritesFromDatabase by weatherDao.getFavourites().collectAsState(initial = emptyList())
    val historyFromDatabase by weatherDao.getHistory().collectAsState(initial = emptyList())

    val favourites = favouritesFromDatabase.map { it.toUiModel() }
    val history = historyFromDatabase.map { it.toUiModel() }

    var selectedTab by remember { mutableIntStateOf(0) }
    var showDetails by remember { mutableStateOf(false) }

    var currentWeather by remember {
        mutableStateOf(
            WeatherUiModel(
                city = "Szczecin",
                country = "Poland",
                temperatureCelsius = 18,
                condition = "Partly cloudy",
                humidity = "64%",
                wind = "12 km/h",
                feelsLikeCelsius = 17,
                pressure = "1012 hPa",
                uvIndex = "Moderate",
                sunrise = "06:12",
                sunset = "20:45"
            )
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
                NavigationBar(
                    tonalElevation = 12.dp
                ) {
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
                onBackClick = {
                    showDetails = false
                }
            )
        } else {
            when (selectedTab) {
                0 -> {
                    HomeScreen(
                        modifier = Modifier.padding(paddingValues),
                        weather = currentWeather,
                        isFavourite = favourites.any { it.city == currentWeather.city },
                        onFavouriteClick = {
                            coroutineScope.launch {
                                val alreadyFavourite = favourites.any {
                                    it.city == currentWeather.city
                                }

                                if (alreadyFavourite) {
                                    weatherDao.deleteFavourite(currentWeather.city)
                                } else {
                                    weatherDao.saveFavourite(currentWeather.toFavouriteEntity())
                                }
                            }
                        },
                        onDetailsClick = {
                            showDetails = true
                        }
                    )
                }

                1 -> {
                    SearchScreen(
                        modifier = Modifier.padding(paddingValues),
                        onSearch = { selectedWeather ->
                            currentWeather = selectedWeather

                            coroutineScope.launch {
                                weatherDao.saveHistory(selectedWeather.toHistoryEntity())
                            }

                            selectedTab = 0
                        }
                    )
                }

                2 -> {
                    FavouritesScreen(
                        modifier = Modifier.padding(paddingValues),
                        favourites = favourites,
                        onCityClick = { selectedWeather ->
                            currentWeather = selectedWeather
                            selectedTab = 0
                        }
                    )
                }

                3 -> {
                    HistoryScreen(
                        modifier = Modifier.padding(paddingValues),
                        history = history,
                        onCityClick = { selectedWeather ->
                            currentWeather = selectedWeather
                            selectedTab = 0
                        }
                    )
                }

                4 -> {
                    ProjectInfoScreen(
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}