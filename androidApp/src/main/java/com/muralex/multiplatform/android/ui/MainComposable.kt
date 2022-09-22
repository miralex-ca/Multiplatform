package com.muralex.multiplatform.android.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.muralex.multiplatform.viewmodel.DKMPViewModel
import com.muralex.multiplatform.android.navigation.Router


@Composable
fun MainComposable(model: DKMPViewModel) {
    val appState by model.stateFlow.collectAsState()
    val dkmpNav = appState.getNavigation(model)
    dkmpNav.Router()
}

