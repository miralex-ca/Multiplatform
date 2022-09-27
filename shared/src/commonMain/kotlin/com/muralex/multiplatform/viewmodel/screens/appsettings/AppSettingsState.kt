package com.muralex.multiplatform.viewmodel.screens.appsettings

import com.muralex.multiplatform.datalayer.sources.localsettings.OpenFromList
import com.muralex.multiplatform.datalayer.sources.localsettings.ThemeMode
import com.muralex.multiplatform.viewmodel.ScreenState

// here is the data class defining the state for this screen

data class AppSettingsState(
    val isLoading: Boolean = false,
    val savedThemeMode: Int = 0,
    val openFromList: Int = 0,
    val sourceCountry: Int = 0,
    val apiDataSource: Int = 0,
    ) : ScreenState


