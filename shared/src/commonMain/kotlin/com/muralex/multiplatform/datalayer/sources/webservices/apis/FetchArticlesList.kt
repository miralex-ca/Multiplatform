package com.muralex.multiplatform.datalayer.sources.webservices.apis


import com.muralex.multiplatform.datalayer.objects.NetworkResult
import com.muralex.multiplatform.datalayer.objects.apis.NewsApiResponse
import com.muralex.multiplatform.datalayer.objects.apis.NewsDto
import com.muralex.multiplatform.datalayer.sources.webservices.ApiClient
import io.ktor.client.call.*
import io.ktor.client.statement.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

suspend fun ApiClient.fetchNewsList(url: String): NetworkResult<NewsApiResponse> {
    return getResponse(url, true)
}



