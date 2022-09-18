package com.muralex.multiplatform.viewmodel.screens.favoriteslist

import com.muralex.multiplatform.datalayer.functions.getFavoriteListData
import com.muralex.multiplatform.viewmodel.Navigation
import com.muralex.multiplatform.viewmodel.screens.ScreenInitSettings

// INIZIALIZATION settings for this screen
// this is what should be implemented:
// - a data class implementing the ScreenParams interface, which defines the parameters to the passed to the screen
// - Navigation extension function taking the ScreenParams class as an argument, return the ScreenInitSettings for this screen
// to understand the initialization behaviour, read the comments in the ScreenInitSettings.kt file


fun Navigation.initFavoriteList() = ScreenInitSettings (
    title = "Bookmarks",
    initState = { FavoriteListState(isLoading = true) },
    callOnInit = {
        val listData = dataRepository.getFavoriteListData()

        // update state, after retrieving data from the repository
        stateManager.updateScreen(FavoriteListState::class) {
            it.copy(
                isLoading = false,
                articlesListItems = listData,
            )
        }
    },
    reinitOnEachNavigation = true, // in this way favourites can refresh
)