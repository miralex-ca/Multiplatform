package com.muralex.multiplatform.viewmodel.screens.webviewdetail

import com.muralex.multiplatform.datalayer.objects.ArticlesListData
import com.muralex.multiplatform.viewmodel.ScreenState

// here is the data class defining the state for this screen

data class ArticleWebviewState (
    val isLoading: Boolean = false,
    val articleInfo : ArticleWebviewData = ArticleWebviewData(),
): ScreenState


/********** property classes **********/

data class ArticleWebviewData (
    val _data : ArticlesListData = ArticlesListData()
)
