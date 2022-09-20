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
        title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        url = "https://helpx.adobe.com/content/dam/help/en/photoshop/using/convert-color-image-black-white/jcr_content/main-pars/before_and_after/image-before/Landscape-Color.jpg",
        description = "",
        image = "",
        text = "",
        publishedAt = "",
        publishedTime = 0L,
        source = ""
    ),


    val isFavorite: Boolean = false
)
