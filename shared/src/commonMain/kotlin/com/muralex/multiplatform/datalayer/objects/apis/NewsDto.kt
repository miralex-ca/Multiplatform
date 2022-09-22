package com.muralex.multiplatform.datalayer.objects.apis

import com.muralex.multiplatform.datalayer.objects.ArticleData
import kotlinx.datetime.toInstant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class NewsDto(
    @SerialName("author")
    val author: String?,
    @SerialName("content")
    val content: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("publishedAt")
    val publishedAt: String?,
    @SerialName("source")
    val source: Source?,
    @SerialName("title")
    val title: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("urlToImage")
    val urlToImage: String?,
) {
    val entity: ArticleData
        get() = ArticleData(
            title = title ?: "",
            description = description?.trim() ?: "",
            text = content ?: "",
            url = url ?: "",
            image = urlToImage ?: "",
            source = source?.name ?: "",
            publishedAt = publishedAt ?: "",
            publishedTime = processDate(publishedAt)
        )
}

val List<NewsDto>.entity get() = map { it.entity }

private fun processDate(date: String?): Long {
    var published = 0L
    try {
        published = date?.toInstant()?.toEpochMilliseconds() ?: 0L
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return published
}


@Serializable
data class NewsApiResponse(
    @SerialName("articles")
    val articles: List<NewsDto>?,
)

@Serializable
data class Source(
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String?,
)

