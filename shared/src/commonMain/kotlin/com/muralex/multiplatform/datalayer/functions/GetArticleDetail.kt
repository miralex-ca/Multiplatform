package com.muralex.multiplatform.datalayer.functions

import com.muralex.multiplatform.datalayer.Repository
import com.muralex.multiplatform.datalayer.objects.ArticleData
import com.muralex.multiplatform.viewmodel.screens.articledetail.ArticleDetail
import com.muralex.multiplatform.viewmodel.screens.articleslist.ArticlesListItem

suspend fun Repository.getArticleDetail(article: String): ArticleDetail = withRepoContext {

    val articleData = runtimeCache.articlesList.find {
        it.title == article || it.url == article
    } ?: ArticleData(
        title = "Article not found",
        url = "",
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