package com.muralex.multiplatform.viewmodel.screens.articledetail

import com.muralex.multiplatform.datalayer.objects.ArticlesListData
import com.muralex.multiplatform.viewmodel.ScreenState

// here is the data class defining the state for this screen

data class ArticleDetailState (
    val isLoading: Boolean = false,
    val articleInfo : ArticleDetail = ArticleDetail(),
): ScreenState


/********** property classes **********/

data class ArticleDetail (
    val _data : ArticlesListData = ArticlesListData(),
    val isFavorite: Boolean = false
)
