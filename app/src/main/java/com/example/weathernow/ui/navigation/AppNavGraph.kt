package com.example.weathernow.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.weathernow.data.mapper.toForecastUiModels
import com.example.weathernow.data.mapper.toHistoryEntity
import com.example.weathernow.data.mapper.toUiModel
import com.example.weathernow.data.remote.WeatherRemoteDataSource
import com.example.weathernow.data.settings.SettingsDataStore
import com.example.weathernow.ui.detail.DetailScreen
import com.example.weathernow.ui.favourites.FavouritesScreen
import com.example.weathernow.ui.history.HistoryScreen
import com.example.weathernow.ui.home.HomeScreen
import com.example.weathernow.ui.project.ProjectInfoScreen
import com.example.weathernow.ui.search.SearchScreen
import com.example.weathernow.ui.settings.SettingsScreen
import kotlinx.coroutines.launch

@Composable
fun AppNavGraph() {
    val context = LocalContext.current
    val database = remember { WeatherDatabase.getDatabase(context) }
    val weatherDao = remember { database.weatherDao() }
    val remoteDataSource = remember { WeatherRemoteDataSource() }
    val settingsDataStore = remember { SettingsDataStore(context) }
    val coroutineScope = rememberCoroutineScope()

    val favouriteEntities by weatherDao.getFavourites().collectAsState(initial = emptyList())
    val historyEntities by weatherDao.getHistory().collectAsState(initial = emptyList())
    val useFahrenheit by settingsDataStore.useFahrenheit.collectAsState(initial = false)

    val favourites = favouriteEntities.map { it.toUiModel() }
    val history = historyEntities.map { it.toUiModel() }

    var selectedTab by remember { mutableIntStateOf(0) }
    var showDetails by remember { mutableStateOf(false) }

    var forecast by remember { mutableStateOf<List<ForecastUiModel>>(emptyList()) }
    var isForecastLoading by remember { mutableStateOf(false) }
    var forecastError by remember { mutableStateOf<String?>(null) }

    val availableCities = remember {
        listOf(
            WeatherUiModel("Szczecin", "Poland", 18, "Partly cloudy", "64%", "12 km/h", 17),
            WeatherUiModel("Madrid", "Spain", 24, "Sunny", "40%", "8 km/h", 25),
            WeatherUiModel("London", "United Kingdom", 14, "Rainy", "78%", "16 km/h", 13),
            WeatherUiModel("Berlin", "Germany", 16, "Cloudy", "60%", "10 km/h", 15),
            WeatherUiModel("Paris", "France", 20, "Clear sky", "55%", "9 km/h", 21),
            WeatherUiModel("Rome", "Italy", 26, "Sunny", "38%", "7 km/h", 27),
            WeatherUiModel("Amsterdam", "Netherlands", 13, "Rainy", "82%", "18 km/h", 12)
        )
    }

    var currentWeather by remember { mutableStateOf(availableCities.first()) }

    fun loadForecastForCity(city: String) {
        coroutineScope.launch {
            isForecastLoading = true
            forecastError = null

            try {
                val response = remoteDataSource.getForecast(city)
                forecast = response.toForecastUiModels()

                if (forecast.isEmpty()) {
                    forecastError = "Forecast data is empty for this city."
                }
            } catch (e: Exception) {
                forecast = emptyList()
                forecastError = "Forecast error: ${e.message ?: "unknown error"}"
            } finally {
                isForecastLoading = false
            }
        }
    }

    fun selectRealWeather(selectedWeather: WeatherUiModel) {
        currentWeather = selectedWeather
        showDetails = false
        selectedTab = 0

        coroutineScope.launch {
            weatherDao.saveHistory(selectedWeather.toHistoryEntity())
        }

        loadForecastForCity(selectedWeather.city)
    }

    LaunchedEffect(Unit) {
        loadForecastForCity(currentWeather.city)
    }

    val items = listOf(
        BottomNavItem("Home", Icons.Default.Home),
        BottomNavItem("Search", Icons.Default.Search),
        BottomNavItem("Favs", Icons.Default.Favorite),
        BottomNavItem("History", Icons.Default.History),
        BottomNavItem("Project", Icons.Default.Info),
        BottomNavItem("Settings", Icons.Default.Settings)
    )

    Scaffold(
        bottomBar = {
            if (!showDetails) {
                NavigationBar(tonalElevation = 12.dp) {
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
                useFahrenheit = useFahrenheit,
                onBackClick = {
                    showDetails = false
                }
            )
        } else {
            when (selectedTab) {
                0 -> HomeScreen(
                    modifier = Modifier.padding(paddingValues),
                    weather = currentWeather,
                    forecast = forecast,
                    isForecastLoading = isForecastLoading,
                    forecastError = forecastError,
                    isFavourite = favourites.any { it.city == currentWeather.city },
                    useFahrenheit = useFahrenheit,
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

                1 -> SearchScreen(
                    modifier = Modifier.padding(paddingValues),
                    availableCities = availableCities,
                    useFahrenheit = useFahrenheit,
                    onSearch = { selectedWeather ->
                        selectRealWeather(selectedWeather)
                    }
                )

                2 -> FavouritesScreen(
                    modifier = Modifier.padding(paddingValues),
                    favourites = favourites,
                    useFahrenheit = useFahrenheit,
                    onCityClick = { selectedWeather ->
                        selectRealWeather(selectedWeather)
                    }
                )

                3 -> HistoryScreen(
                    modifier = Modifier.padding(paddingValues),
                    history = history,
                    useFahrenheit = useFahrenheit,
                    onCityClick = { selectedWeather ->
                        selectRealWeather(selectedWeather)
                    }
                )

                4 -> ProjectInfoScreen(
                    modifier = Modifier.padding(paddingValues)
                )

                5 -> SettingsScreen(
                    modifier = Modifier.padding(paddingValues),
                    useFahrenheit = useFahrenheit,
                    onUnitChange = { value ->
                        coroutineScope.launch {
                            settingsDataStore.saveUseFahrenheit(value)
                        }
                    }
                )
            }
        }
    }
}