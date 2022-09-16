package com.muralex.multiplatform.datalayer.sources.webservices.apis


import com.muralex.multiplatform.datalayer.sources.webservices.ApiClient
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

suspend fun ApiClient.fetchNewsList(): NewsApiResponse? {
    return getResponse("")
}


//@Serializable
//data class CountriesListResponse(
//    @SerialName("data") val data : List<CountryListData>,
//    @SerialName("err") val error : String? = null,
//)

@Serializable
data class NewsApiResponse (
    @SerialName("articles")
    val articles: List<News>?
)

@Serializable
data class News (
    @SerialName("title")
    val title: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("urlToImage")
    val urlToImage: String?
)