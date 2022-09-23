package com.muralex.multiplatform.datalayer.functions

import com.muralex.multiplatform.datalayer.Repository
import com.muralex.multiplatform.datalayer.objects.ArticleData
import com.muralex.multiplatform.datalayer.objects.NetworkResult
import com.muralex.multiplatform.datalayer.objects.apis.NewsApiResponse
import com.muralex.multiplatform.datalayer.objects.apis.entity
import com.muralex.multiplatform.viewmodel.screens.articleslist.ArticlesListItem
import com.muralex.multiplatform.datalayer.sources.webservices.apis.fetchNewsList

suspend fun Repository.getArticlesListData(): List<ArticleData> = withRepoContext {
    if (runtimeCache.articlesList.isEmpty()) {
        runtimeCache.articlesList = webservices.fetchNewsList()
            .body?.articles?.entity?.imageUpfront()
            ?: emptyList()
    }
    runtimeCache.articlesList
}

fun List<ArticleData>.imageUpfront() : List<ArticleData> {
    //println("list: " + this)

    val orderedList = toMutableList()
     map { articleData ->
        if (articleData.image.trim().isEmpty()) {
            orderedList.add(orderedList.removeAt(orderedList.indexOf(articleData)))
        }
    }
    return orderedList
}