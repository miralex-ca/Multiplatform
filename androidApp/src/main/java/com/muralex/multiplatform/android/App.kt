package com.muralex.multiplatform.android

import android.app.Application
import androidx.lifecycle.*
import com.muralex.multiplatform.shared.viewmodel.getAndroidInstance
import com.muralex.multiplatform.viewmodel.DKMPViewModel

class DKMPApp : Application() {

    lateinit var model: DKMPViewModel

    override fun onCreate() {
        super.onCreate()
        model = DKMPViewModel.Factory.getAndroidInstance(this)
        
        val appLifecycleObserver = AppLifecycleObserver(model)
        ProcessLifecycleOwner.get().lifecycle.addObserver(appLifecycleObserver)
    }
}

class AppLifecycleObserver (private val model: DKMPViewModel) : DefaultLifecycleObserver {

    private var isInBackground: Boolean = false


    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        if (isInBackground && model.stateFlow.value.recompositionIndex > 0) { // not callign at app startup
            model.navigation.onReEnterForeground()
            isInBackground = false
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        model.navigation.onEnterBackground()
        isInBackground = true
        super.onStop(owner)
    }

}