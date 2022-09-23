package com.muralex.multiplatform.viewmodel.screens.favoriteslist

import com.muralex.multiplatform.datalayer.functions.getFavoriteListData
import com.muralex.multiplatform.viewmodel.Navigation
import com.muralex.multiplatform.viewmodel.screens.ScreenInitSettings


fun Navigation.initFavoriteList() = ScreenInitSettings (
    title = "Bookmarks",
    initState = { FavoriteListState(isLoading = true) },
    callOnInit = {
        val listData = dataRepository.getFavoriteListData()
        stateManager.updateScreen(FavoriteListState::class) {
            it.copy(
                isLoading = false,
                articlesListItems = listData,
            )
        }
    },
    reinitOnEachNavigation = true,
)