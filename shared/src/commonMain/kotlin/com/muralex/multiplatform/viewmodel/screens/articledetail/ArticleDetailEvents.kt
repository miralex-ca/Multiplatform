package com.muralex.multiplatform.viewmodel.screens.articledetail

import com.muralex.multiplatform.viewmodel.Events
import com.muralex.multiplatform.viewmodel.screens.articleslist.ArticlesListState
import com.muralex.multiplatform.viewmodel.screens.favoriteslist.FavoriteListState


/********** NO EVENT FUNCTION IS DEFINED ON THIS SCREEN **********/

fun Events.addToFavorite(article: String) = screenCoroutine {
    // todo set favorite
    // update state with new favorites map, after toggling the value for the specified country
    stateManager.updateScreen(ArticlesListState::class) { it }
}

fun Events.removeFromFavorite(article: String) = screenCoroutine {
    // todo remove favorite
    // update state with new favorites map, after toggling the value for the specified country
    stateManager.updateScreen(FavoriteListState::class) { it }
}

