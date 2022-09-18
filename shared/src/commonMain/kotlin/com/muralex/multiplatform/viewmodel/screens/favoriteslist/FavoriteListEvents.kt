package com.muralex.multiplatform.viewmodel.screens.favoriteslist

import com.muralex.multiplatform.viewmodel.Events

/********** EVENT functions, called directly by the UI layer **********/

fun Events.removeFavorite(countryName: String) = screenCoroutine {
    // todo remove favorite
    // update state with new favorites map, after toggling the value for the specified country
    stateManager.updateScreen(FavoriteListState::class) { it }
}
