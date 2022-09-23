package com.muralex.multiplatform.datalayer.functions

import com.muralex.multiplatform.datalayer.Repository
import com.muralex.multiplatform.datalayer.objects.ArticleData
import com.muralex.multiplatform.datalayer.sources.localdb.getBookmark
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

    val isFavorite = checkBookmark(articleData.url)
    val data = ArticlesListItem(articleData)

    ArticleDetail(
        _data = data._data,
        isFavorite = isFavorite,
    )
}


suspend fun Repository.getBookmarkDetail(article: String): ArticleDetail = withRepoContext {

    val articleData = localDb.getBookmark(article)
    ArticleDetail(
        _data = articleData,
        isFavorite = true,
    )

}