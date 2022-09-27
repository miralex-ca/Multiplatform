package com.muralex.multiplatform.datalayer.functions

import com.muralex.multiplatform.datalayer.Repository
import com.muralex.multiplatform.datalayer.objects.ArticleData
import com.muralex.multiplatform.datalayer.objects.apis.entity
import com.muralex.multiplatform.datalayer.sources.localsettings.ApiDataSource
import com.muralex.multiplatform.datalayer.sources.localsettings.MySettings
import com.muralex.multiplatform.datalayer.sources.webservices.ApiClient
import com.muralex.multiplatform.datalayer.sources.webservices.apis.fetchNewsList

suspend fun Repository.getArticlesListData(): List<ArticleData> = withRepoContext {

    val url = buildUrlFromConfig(localSettings)

    if (runtimeCache.articlesList.isEmpty()) {
        runtimeCache.articlesList = webservices.fetchNewsList(url )
            .body?.articles?.entity?.imageUpfront()
            ?: emptyList()
    }
    runtimeCache.articlesList
}

fun buildUrlFromConfig(localSettings: MySettings): String {
    val apiSourceTest = localSettings.apiDataSource == ApiDataSource.TESTAPI.name
    val sourceCountry = localSettings.sourceCountry.lowercase()

    return if (apiSourceTest) {
        ApiClient.getTestUrl()+ ApiClient.getTestApiEndpointByCountry(sourceCountry)
    } else {
        ApiClient.getBaseUrl() + ApiClient.getNewsApiEndpointByCountry(sourceCountry)
    }
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