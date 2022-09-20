package com.muralex.multiplatform.viewmodel.screens.articleslist

import com.muralex.multiplatform.datalayer.objects.ArticleData
import com.muralex.multiplatform.viewmodel.ScreenState

// here is the data class defining the state for this screen

data class ArticlesListState (
    val isLoading : Boolean = false,
    val articlesListItems : List<ArticlesListItem> = emptyList()
): ScreenState

/********** property classes **********/

enum class ArticlesListType { ALL, FAVORITES }

data class ArticlesListItem (
    val _data : ArticleData,
) {
    val name = _data.title
    val desc = _data.description
}