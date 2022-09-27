package com.muralex.multiplatform.datalayer.sources.localsettings

import com.muralex.multiplatform.viewmodel.screens.Level1Navigation
import com.russhwolf.settings.Settings
import com.russhwolf.settings.string

class MySettings(s: Settings) {

    var savedLevel1URI by s.string(defaultValue = Level1Navigation.AllArticles.screenIdentifier.URI)

    var savedThemeMode by s.string(defaultValue = ThemeMode.defaultStr())

    var openFromList by s.string(defaultValue = OpenFromList.defaultStr())

    var sourceCountry by s.string(defaultValue = SourceCountry.defaultStr())

    var apiDataSource by s.string(defaultValue = ApiDataSource.defaultStr())

}


enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM;

    companion object {
        fun defaultStr() = LIGHT.name
    }
}

enum class OpenFromList {
    SHEET,
    BOTTOM;

    companion object {
        fun defaultStr() = BOTTOM.name
    }
}

enum class SourceCountry {
    CA,
    US,
    GB,
    FR,
    DE,
    IT;

    companion object {
        fun defaultStr() = SourceCountry.CA.name
    }
}

enum class ApiDataSource {
    NEWSAPI,
    TESTAPI;

    companion object {
        fun defaultStr() = NEWSAPI.name
    }
}