package com.muralex.multiplatform.viewmodel.screens.appsettings

import com.muralex.multiplatform.datalayer.functions.getArticleDetail
import com.muralex.multiplatform.viewmodel.Navigation
import com.muralex.multiplatform.viewmodel.ScreenParams
import com.muralex.multiplatform.viewmodel.screens.ScreenInitSettings
import kotlinx.serialization.Serializable


// INIZIALIZATION settings for this screen
// to understand the initialization behaviour, read the comments in the ScreenInitSettings.kt file

@Serializable // Note: ScreenParams should always be set as Serializable
data class AppSettingsParams(val title: String) : ScreenParams

fun Navigation.initAppSettings(params: AppSettingsParams) = ScreenInitSettings (
    title = params.title,
    initState = { AppSettingsState(isLoading = true) },
    callOnInit = {

        val articleInfo = dataRepository.getArticleDetail(params.title)
        // update state, after retrieving data from the repository
        stateManager.updateScreen(AppSettingsState::class) {
            it.copy(
                isLoading = false,
            )
        }
    }
)