package com.muralex.multiplatform.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.muralex.multiplatform.android.theme.MyApplicationTheme
import com.muralex.multiplatform.android.ui.MainComposable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        val model = (application as DKMPApp).model

        setContent {
            MainComposable(model)
        }
    }
}


