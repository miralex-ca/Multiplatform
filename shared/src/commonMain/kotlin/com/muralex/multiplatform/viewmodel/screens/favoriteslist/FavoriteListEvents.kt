package com.muralex.multiplatform.viewmodel.screens.favoriteslist

import com.muralex.multiplatform.viewmodel.Events

fun Events.removeFavorite(url: String) = screenCoroutine {
    // todo remove favorite from list

    stateManager.updateScreen(FavoriteListState::class) { it }
}
