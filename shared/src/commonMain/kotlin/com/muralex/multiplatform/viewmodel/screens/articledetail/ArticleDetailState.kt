package com.muralex.multiplatform.viewmodel.screens.articledetail

import com.muralex.multiplatform.datalayer.objects.ArticleData
import com.muralex.multiplatform.viewmodel.ScreenState

// here is the data class defining the state for this screen

data class ArticleDetailState (
    val isLoading: Boolean = false,
    val articleInfo : ArticleDetail = ArticleDetail(),
): ScreenState

/********** property classes **********/

data class ArticleDetail (
    val _data : ArticleData = ArticleData(
        title = ".",
        url = "",
        description = "",
        image = "",
        text = "",
        publishedAt = "",
        publishedTime = 0L,
        source = ""
    ),

    val isFavorite: Boolean = false
)
