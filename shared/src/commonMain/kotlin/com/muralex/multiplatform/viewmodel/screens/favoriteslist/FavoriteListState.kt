package com.muralex.multiplatform.viewmodel.screens.favoriteslist

import com.muralex.multiplatform.datalayer.objects.ArticleData
import com.muralex.multiplatform.viewmodel.ScreenState

// here is the data class defining the state for this screen

data class FavoriteListState (
    val isLoading : Boolean = false,
    val articlesListItems : List<FavoriteListItem> = emptyList()
): ScreenState

/********** property classes **********/

data class FavoriteListItem (
    val _data : ArticleData,
) {
    val name = _data.title
    val desc = _data.description
}