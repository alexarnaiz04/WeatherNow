package com.example.weathernow.ui.navigation

import androidx.compose.foundation.layout.Box
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
    val showAdvancedDetails by settingsDataStore.showAdvancedDetails.collectAsState(initial = true)
    val autoRefreshWeather by settingsDataStore.autoRefreshWeather.collectAsState(initial = true)
    val compactCards by settingsDataStore.compactCards.collectAsState(initial = false)

    val favourites = favouriteEntities.map { it.toUiModel() }
    val history = historyEntities.map { it.toUiModel() }

    var selectedTab by remember { mutableIntStateOf(0) }
    var showDetails by remember { mutableStateOf(false) }

    var forecast by remember { mutableStateOf<List<ForecastUiModel>>(emptyList()) }
    var isForecastLoading by remember { mutableStateOf(false) }
    var forecastError by remember { mutableStateOf<String?>(null) }

    val defaultWeather = remember {
        WeatherUiModel(
            city = "Szczecin",
            country = "Poland",
            temperatureCelsius = 18,
            condition = "Partly cloudy",
            humidity = "64%",
            wind = "12 km/h",
            feelsLikeCelsius = 17,
            pressure = "1013 hPa",
            uvIndex = "Low",
            sunrise = "06:00",
            sunset = "20:00"
        )
    }

    val availableCities = remember {
        listOf(
            defaultWeather,
            WeatherUiModel("Madrid", "Spain", 24, "Sunny", "40%", "8 km/h", 25, "1016 hPa", "Moderate", "07:10", "21:25"),
            WeatherUiModel("London", "United Kingdom", 14, "Rainy", "78%", "16 km/h", 13, "1008 hPa", "Low", "05:40", "20:45"),
            WeatherUiModel("Berlin", "Germany", 16, "Cloudy", "60%", "10 km/h", 15, "1011 hPa", "Low", "05:55", "20:30"),
            WeatherUiModel("Paris", "France", 20, "Clear sky", "55%", "9 km/h", 21, "1014 hPa", "Moderate", "06:15", "21:00"),
            WeatherUiModel("Rome", "Italy", 26, "Sunny", "38%", "7 km/h", 27, "1017 hPa", "High", "06:25", "20:35"),
            WeatherUiModel("Amsterdam", "Netherlands", 13, "Rainy", "82%", "18 km/h", 12, "1007 hPa", "Low", "05:50", "20:55")
        )
    }

    var currentWeather by remember { mutableStateOf(defaultWeather) }

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
                forecastError = "Forecast unavailable. Check your connection."
            } finally {
                isForecastLoading = false
            }
        }
    }

    fun openWeatherFromCity(
        city: String,
        saveInHistory: Boolean
    ) {
        coroutineScope.launch {
            try {
                val response = remoteDataSource.searchWeather(city)
                val realWeather = response.toUiModel()

                currentWeather = realWeather
                showDetails = false
                selectedTab = 0

                if (saveInHistory) {
                    weatherDao.saveHistory(realWeather.toHistoryEntity())
                }

                val isFavourite = favourites.any {
                    it.city.equals(realWeather.city, ignoreCase = true)
                }

                if (isFavourite) {
                    weatherDao.saveFavourite(realWeather.toFavouriteEntity())
                }

                loadForecastForCity(realWeather.city)
            } catch (e: Exception) {
                forecastError = "Could not refresh weather. Showing last available data."
                loadForecastForCity(city)
            }
        }
    }

    fun selectWeather(selectedWeather: WeatherUiModel) {
        if (autoRefreshWeather) {
            openWeatherFromCity(
                city = selectedWeather.city,
                saveInHistory = true
            )
        } else {
            currentWeather = selectedWeather
            showDetails = false
            selectedTab = 0

            coroutineScope.launch {
                weatherDao.saveHistory(selectedWeather.toHistoryEntity())
            }

            loadForecastForCity(selectedWeather.city)
        }
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
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            if (showDetails) {
                DetailScreen(
                    weather = currentWeather,
                    useFahrenheit = useFahrenheit,
                    onBackClick = {
                        showDetails = false
                    }
                )
            } else {
                when (selectedTab) {
                    0 -> HomeScreen(
                        weather = currentWeather,
                        forecast = forecast,
                        isForecastLoading = isForecastLoading,
                        forecastError = forecastError,
                        isFavourite = favourites.any {
                            it.city.equals(currentWeather.city, ignoreCase = true)
                        },
                        useFahrenheit = useFahrenheit,
                        showAdvancedDetails = showAdvancedDetails,
                        onFavouriteClick = {
                            coroutineScope.launch {
                                val alreadyFavourite = favourites.any {
                                    it.city.equals(currentWeather.city, ignoreCase = true)
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
                        availableCities = availableCities,
                        useFahrenheit = useFahrenheit,
                        onSearch = { selectedWeather ->
                            selectWeather(selectedWeather)
                        }
                    )

                    2 -> FavouritesScreen(
                        favourites = favourites,
                        useFahrenheit = useFahrenheit,
                        compactCards = compactCards,
                        onCityClick = { selectedWeather ->
                            selectWeather(selectedWeather)
                        }
                    )

                    3 -> HistoryScreen(
                        history = history,
                        useFahrenheit = useFahrenheit,
                        onCityClick = { selectedWeather ->
                            selectWeather(selectedWeather)
                        },
                        onDeleteItem = { selectedWeather ->
                            coroutineScope.launch {
                                weatherDao.deleteHistoryItem(selectedWeather.city)
                            }
                        },
                        onClearHistory = {
                            coroutineScope.launch {
                                weatherDao.clearHistory()
                            }
                        }
                    )

                    4 -> ProjectInfoScreen()

                    5 -> SettingsScreen(
                        useFahrenheit = useFahrenheit,
                        showAdvancedDetails = showAdvancedDetails,
                        autoRefreshWeather = autoRefreshWeather,
                        compactCards = compactCards,
                        onUnitChange = { value ->
                            coroutineScope.launch {
                                settingsDataStore.saveUseFahrenheit(value)
                            }
                        },
                        onShowAdvancedDetailsChange = { value ->
                            coroutineScope.launch {
                                settingsDataStore.saveShowAdvancedDetails(value)
                            }
                        },
                        onAutoRefreshWeatherChange = { value ->
                            coroutineScope.launch {
                                settingsDataStore.saveAutoRefreshWeather(value)
                            }
                        },
                        onCompactCardsChange = { value ->
                            coroutineScope.launch {
                                settingsDataStore.saveCompactCards(value)
                            }
                        }
                    )
                }
            }
        }
    }
}b