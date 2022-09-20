package com.muralex.multiplatform.datalayer.objects

data class ArticleData(
    val title: String,
    val description: String,
    val text: String,
    val url: String,
    val image: String,
    val source: String,
    val publishedAt: String,
    val publishedTime: Long
)