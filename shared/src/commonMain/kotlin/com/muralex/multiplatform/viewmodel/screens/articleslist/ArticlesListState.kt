package com.muralex.multiplatform.viewmodel.screens.articleslist

import com.muralex.multiplatform.datalayer.objects.ArticlesListData
import com.muralex.multiplatform.viewmodel.ScreenState

// here is the data class defining the state for this screen

data class ArticlesListState (
    val isLoading : Boolean = false,
    val articlesListItems : List<ArticlesListItem> = emptyList()
): ScreenState

/********** property classes **********/

enum class ArticlesListType { ALL, FAVORITES }

data class ArticlesListItem (
    val _data : ArticlesListData,
) {
    // in the ViewModel classes, our computed properties only do UI-formatting operations
    // (the arithmetical operations, such as calculating a percentage, should happen in the DataLayer classes)
    val name = _data.title
    val desc = _data.desc
}