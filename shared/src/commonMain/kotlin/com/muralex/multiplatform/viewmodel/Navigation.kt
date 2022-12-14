package com.muralex.multiplatform.viewmodel

import com.muralex.multiplatform.viewmodel.screens.Level1Navigation
import com.muralex.multiplatform.viewmodel.screens.Screen
import com.muralex.multiplatform.viewmodel.screens.navigationSettings


class Navigation(val stateManager : StateManager) {

    init {
        var startScreenIdentifier = navigationSettings.homeScreen.screenIdentifier
        if (navigationSettings.saveLastLevel1Screen) {
            startScreenIdentifier = ScreenIdentifier.getByURI(dataRepository.localSettings.savedLevel1URI) ?: startScreenIdentifier
        }
        navigateByScreenIdentifier(startScreenIdentifier)
    }

    val stateProvider by lazy { StateProvider(stateManager) }
    val events by lazy { Events(stateManager) }

    fun getTitle(screenIdentifier: ScreenIdentifier) : String {
        val screenInitSettings = screenIdentifier.getScreenInitSettings(this)
        return screenInitSettings.title
    }

    val dataRepository
        get() = stateManager.dataRepository

    val currentScreenIdentifier : ScreenIdentifier
        get() = stateManager.currentScreenIdentifier

    val currentLevel1ScreenIdentifier : ScreenIdentifier
        get() = stateManager.currentLevel1ScreenIdentifier

    val only1ScreenInBackstack : Boolean
        get() = stateManager.only1ScreenInBackstack


    // used by the Router composable in Compose apps
    // it returns a list of screens whose state has been removed, so they should also be removed from Compose's SaveableStateHolder
    val screenStatesToRemove : List<ScreenIdentifier>
        get() = stateManager.getScreenStatesToRemove()

    // used by the Router view in SwiftUI apps
    // it returns the list of Level1 screens to be rendered inside a SwiftUI's ZStack
    val level1ScreenIdentifiers : List<ScreenIdentifier>
        get() = stateManager.getLevel1ScreenIdentifiers()

    fun getNavigationLevelsMap(level1ScreenIdentifier: ScreenIdentifier) : Map<Int,ScreenIdentifier>? {
        return stateManager.verticalNavigationLevels[level1ScreenIdentifier.URI]
    }

    fun isInCurrentVerticalBackstack(screenIdentifier: ScreenIdentifier) : Boolean {
        stateManager.currentVerticalBackstack.forEach {
            if (it.URI == screenIdentifier.URI) {
                return true
            }
        }
        return false
    }


    fun navigate(screen: Screen, params: ScreenParams? = null) {
        navigateByScreenIdentifier(ScreenIdentifier.get(screen,params))
    }

    fun navigateByLevel1Menu(level1NavigationItem: Level1Navigation) {
        val navigationLevelsMap = getNavigationLevelsMap(level1NavigationItem.screenIdentifier)
        if (navigationLevelsMap==null) {
            navigateByScreenIdentifier(level1NavigationItem.screenIdentifier)
        } else {
            navigationLevelsMap.keys.sorted().forEach {
                navigateByScreenIdentifier(navigationLevelsMap[it]!!)
            }
        }

    }

    fun navigateByScreenIdentifier(screenIdentifier: ScreenIdentifier) {

        val screenInitSettings = screenIdentifier.getScreenInitSettings(this)
        stateManager.addScreen(screenIdentifier, screenInitSettings)
        if (navigationSettings.saveLastLevel1Screen && screenIdentifier.screen.navigationLevel == 1) {
            dataRepository.localSettings.savedLevel1URI = screenIdentifier.URI
        }
    }

    fun exitScreen(screenIdentifier: ScreenIdentifier? = null, triggerRecomposition: Boolean = true) {
        val sID = screenIdentifier ?: currentScreenIdentifier

        stateManager.removeScreen(sID)
        if (triggerRecomposition) {
            navigateByScreenIdentifier(currentScreenIdentifier)
        }
    }


    fun onReEnterForeground() {
        // not called at app startup, but only when reentering the app after it was in background
        val reinitializedScreens = stateManager.reinitScreenScopes()
        stateManager.triggerRecomposition()
        reinitializedScreens.forEach {
            it.getScreenInitSettings(this).apply {
                if (callOnInitAlsoAfterBackground) {
                    stateManager.runInScreenScope { callOnInit(stateManager) }
                }
            }
        }
    }

    fun onEnterBackground() {
        stateManager.cancelScreenScopes()
    }

    fun onChangeOrientation() {
        stateManager.triggerRecomposition()
    }


}