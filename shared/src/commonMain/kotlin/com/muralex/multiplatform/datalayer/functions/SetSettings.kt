package com.muralex.multiplatform.datalayer.functions

import com.muralex.multiplatform.datalayer.Repository
import com.muralex.multiplatform.datalayer.sources.localsettings.ApiDataSource
import com.muralex.multiplatform.datalayer.sources.localsettings.OpenFromList
import com.muralex.multiplatform.datalayer.sources.localsettings.SourceCountry
import com.muralex.multiplatform.datalayer.sources.localsettings.ThemeMode

suspend fun Repository.setThemeModeByIndex(index: Int) = withRepoContext {
    val value = ThemeMode.values()[index].name
    localSettings.savedThemeMode = value
}

suspend fun Repository.setOpenFromListByIndex(index: Int) = withRepoContext {
    val value = OpenFromList.values()[index].name
    localSettings.openFromList = value
}

suspend fun Repository.setCountryByIndex(index: Int) = withRepoContext {
    val value = SourceCountry.values()[index].name
    localSettings.sourceCountry = value
    runtimeCache.articlesList = emptyList()
}

suspend fun Repository.setApiSourceByIndex(index: Int) = withRepoContext {
    val value = ApiDataSource.values()[index].name
    localSettings.apiDataSource = value
    runtimeCache.articlesList = emptyList()
}
