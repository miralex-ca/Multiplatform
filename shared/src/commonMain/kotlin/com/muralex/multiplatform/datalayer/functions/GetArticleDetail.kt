package com.muralex.multiplatform.datalayer.functions

import com.muralex.multiplatform.datalayer.Repository
import com.muralex.multiplatform.datalayer.objects.ArticleData
import com.muralex.multiplatform.viewmodel.screens.articledetail.ArticleDetail
import com.muralex.multiplatform.viewmodel.screens.articleslist.ArticlesListItem

suspend fun Repository.getArticleDetail(article: String): ArticleDetail = withRepoContext {

    val articleData = runtimeCache.articlesList.find { it.title == article } ?: ArticleData(
        title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        url = "https://helpx.adobe.com/content/dam/help/en/photoshop/using/convert-color-image-black-white/jcr_content/main-pars/before_and_after/image-before/Landscape-Color.jpg",
        description = "",
        image = "",
        text = "",
        publishedAt = "",
        publishedTime = 0L,
        source = ""
    )

    val data = ArticlesListItem(articleData)

     ArticleDetail(
         _data = data._data,
         isFavorite = false
     )
}