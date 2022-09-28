package com.muralex.multiplatform.android.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.muralex.multiplatform.viewmodel.DKMPViewModel
import com.muralex.multiplatform.android.navigation.Router
import com.muralex.multiplatform.android.theme.MyApplicationTheme
import com.muralex.multiplatform.datalayer.functions.getThemeModeIndex
import com.muralex.multiplatform.datalayer.sources.localsettings.ThemeMode


@Composable
fun MainComposable(model: DKMPViewModel) {
    val appState by model.stateFlow.collectAsState()
    val dkmpNav = appState.getNavigation(model)
    val mode = dkmpNav.dataRepository.localSettings.savedThemeMode

    MyApplicationTheme(ThemeMode.valueOf(mode)) {
        dkmpNav.Router()

    }

}

