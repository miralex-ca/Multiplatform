package com.muralex.multiplatform.viewmodel.screens.appsettings

import com.muralex.multiplatform.datalayer.functions.*
import com.muralex.multiplatform.viewmodel.Events


fun Events.setThemeModeByIndex(index: Int) = screenCoroutine {
    dataRepository.setThemeModeByIndex(index)
    val current = dataRepository.getThemeModeIndex()

    stateManager.updateScreen(AppSettingsState::class) {
        it.copy(savedThemeMode = current)
    }
}

fun Events.setOpenFromListIndex(index: Int) = screenCoroutine {
    dataRepository.setOpenFromListByIndex(index)
    val current = dataRepository.getOpenFromListIndex()

    stateManager.updateScreen(AppSettingsState::class) {
        it.copy(openFromList = current)
    }
}

fun Events.setSourceCountryByIndex(index: Int) = screenCoroutine {
    dataRepository.setCountryByIndex(index)
    val current = dataRepository.getSelectedCountryIndex()

    stateManager.updateScreen(AppSettingsState::class) {
        it.copy(sourceCountry = current)
    }
}

fun Events.setApiSourceIndex(index: Int) = screenCoroutine {
    dataRepository.setApiSourceByIndex(index)
    val current = dataRepository.getApiSourceIndex()

    stateManager.updateScreen(AppSettingsState::class) {
        it.copy(apiDataSource = current)
    }
}