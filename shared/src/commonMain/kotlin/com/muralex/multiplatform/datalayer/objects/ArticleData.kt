package com.muralex.multiplatform.datalayer.objects

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime

data class ArticleData(
    val title: String,
    val description: String,
    val text: String,
    val url: String,
    val image: String,
    val source: String,
    val publishedAt: String,
    val publishedTime: Long,
)

data class ArticleDBData(
    val id: String = "",
    val title: String,
    val desc: String,
    val text: String,
    val source: String,
    val url: String,
    val image: String,
    val publishedTime: Long,
)

fun ArticleDBData.toArticle() : ArticleData {
    return ArticleData(
        title = title,
        description = desc,
        text = text,
        url = url,
        image = image,
        source = source,
        publishedAt = "",
        publishedTime = publishedTime
    )
}

fun List<ArticleDBData>.toArticlesList(): List<ArticleData> {
    return map { it.toArticle()}
}


