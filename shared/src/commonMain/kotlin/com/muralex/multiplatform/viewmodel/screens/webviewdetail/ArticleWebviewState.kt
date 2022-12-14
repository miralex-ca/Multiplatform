package com.muralex.multiplatform.viewmodel.screens.webviewdetail

import com.muralex.multiplatform.datalayer.objects.ArticleData
import com.muralex.multiplatform.viewmodel.ScreenState

data class ArticleWebviewState (
    val isLoading: Boolean = false,
    val articleInfo : ArticleWebviewData = ArticleWebviewData(),
): ScreenState


/********** property classes **********/

data class ArticleWebviewData (
    val _data : ArticleData = ArticleData.getEmpty()
)
