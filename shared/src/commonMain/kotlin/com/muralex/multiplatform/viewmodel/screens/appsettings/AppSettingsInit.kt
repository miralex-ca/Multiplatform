package com.muralex.multiplatform.viewmodel.screens.appsettings

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

        stateManager.updateScreen(AppSettingsState::class) {
            it.copy(
                isLoading = false,
            )
        }
    }
)