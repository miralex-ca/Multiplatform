package com.muralex.multiplatform.datalayer.objects

data class ArticleData(
    val title: String,
    val description: String,
    val text: String,
    val url: String,
    val image: String,
    val source: String,
    val publishedAt: String,
    val publishedTime: Long,
) {
    companion object {
        fun getEmpty() = ArticleData(
            title = "Article not found",
            url = "",
            description = "",
            image = "",
            text = "",
            publishedAt = "",
            publishedTime = 0L,
            source = ""
        )
    }
}

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


