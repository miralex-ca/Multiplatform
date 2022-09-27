package com.muralex.multiplatform.viewmodel.screens.favoriteslist

import com.muralex.multiplatform.datalayer.functions.deleteBookmark
import com.muralex.multiplatform.datalayer.functions.getFavoriteListData
import com.muralex.multiplatform.viewmodel.Events

fun Events.removeFavorite(url: String) = screenCoroutine {

    dataRepository.deleteBookmark(url)

    val listData = dataRepository.getFavoriteListData()

    stateManager.updateScreen(FavoriteListState::class) {
        it.copy(
            articlesListItems = listData,
        )
    }
}
