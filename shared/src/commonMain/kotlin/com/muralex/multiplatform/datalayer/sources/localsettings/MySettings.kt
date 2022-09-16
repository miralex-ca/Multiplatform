package com.muralex.multiplatform.datalayer.sources.localsettings

import com.muralex.multiplatform.viewmodel.screens.Level1Navigation
import com.russhwolf.settings.Settings
import com.russhwolf.settings.long
import com.russhwolf.settings.string

class MySettings (s : Settings) {


    // here we define all our local settings properties,
    // by using the MultiplatformSettings library delegated properties

    var listCacheTimestamp by s.long(defaultValue = 0)
    var savedLevel1URI by s.string(defaultValue = Level1Navigation.AllArticles.screenIdentifier.URI)


}