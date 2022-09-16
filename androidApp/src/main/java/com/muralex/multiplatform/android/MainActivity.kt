package com.muralex.multiplatform.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.muralex.multiplatform.android.theme.MyApplicationTheme
import eu.baroncelli.dkmpsample.composables.MainComposable



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model = (application as DKMPApp).model

        setContent {
            MyApplicationTheme {
                MainComposable(model)
            }
        }
    }
}


