package com.muralex.multiplatform.viewmodel.screens.appsettings

import com.muralex.multiplatform.datalayer.functions.getApiSourceIndex
import com.muralex.multiplatform.datalayer.functions.getOpenFromListIndex
import com.muralex.multiplatform.datalayer.functions.getSelectedCountryIndex
import com.muralex.multiplatform.datalayer.functions.getThemeModeIndex
import com.muralex.multiplatform.viewmodel.Navigation
import com.muralex.multiplatform.viewmodel.ScreenParams
import com.muralex.multiplatform.viewmodel.screens.ScreenInitSettings
import kotlinx.serialization.Serializable

@Serializable
data class AppSettingsParams(val title: String) : ScreenParams

fun Navigation.initAppSettings(params: AppSettingsParams) = ScreenInitSettings (
    title = params.title,
    initState = { AppSettingsState(isLoading = true) },
    callOnInit = {

        val savedThemeModeIndex = dataRepository.getThemeModeIndex()
        val openFromListIndex = dataRepository.getOpenFromListIndex()
        val sourceCountryIndex = dataRepository.getSelectedCountryIndex()
        val apiDataSourceIndex = dataRepository.getApiSourceIndex()

        stateManager.updateScreen(AppSettingsState::class) {
            it.copy(
                isLoading = false,
                savedThemeMode = savedThemeModeIndex,
                openFromList = openFromListIndex,
                sourceCountry = sourceCountryIndex,
                apiDataSource = apiDataSourceIndex
            )
        }
    }
)
