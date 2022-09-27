package com.muralex.multiplatform.datalayer.functions

import com.muralex.multiplatform.datalayer.Repository
import com.muralex.multiplatform.datalayer.objects.ArticleData
import com.muralex.multiplatform.datalayer.sources.localdb.getBookmark
import com.muralex.multiplatform.viewmodel.screens.articledetail.ArticleDetail
import com.muralex.multiplatform.viewmodel.screens.articleslist.ArticlesListItem

suspend fun Repository.getArticleDetail(article: String): ArticleDetail = withRepoContext {

    val articleData = runtimeCache.articlesList.find {
        it.title == article || it.url == article
    } ?: ArticleData.getEmpty()

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


suspend fun Repository.getDetail(url: String): ArticleDetail = withRepoContext {

    var articleData  = runtimeCache.articlesList.find {
         it.url == url
    }

    if (articleData == null) {
        articleData = try {
            localDb.getBookmark(url)
        } catch (e: Exception) {
            ArticleData.getEmpty()
        }
    }

    if (articleData == null) articleData = ArticleData.getEmpty()

    ArticleDetail(
        _data = articleData,
        isFavorite = true,
    )

}