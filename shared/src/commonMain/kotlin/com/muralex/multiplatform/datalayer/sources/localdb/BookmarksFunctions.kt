package com.muralex.multiplatform.datalayer.sources.localdb
import com.muralex.multiplatform.NewsDb
import com.muralex.multiplatform.datalayer.objects.ArticleDBData
import com.muralex.multiplatform.datalayer.objects.ArticleData
import com.muralex.multiplatform.datalayer.objects.toArticle
import com.muralex.multiplatform.datalayer.objects.toArticlesList


fun NewsDb.getBookmarksList(): List<ArticleData> {
    return tableQueries.selectAll(::ArticleDBData).executeAsList().toArticlesList()
}

fun NewsDb.checkBookmark(url: String): Boolean {
    return tableQueries.checkBookmark(url).executeAsOne() > 0
}

fun NewsDb.getBookmark(url: String): ArticleData {
    return tableQueries.getBookmark(url, ::ArticleDBData).executeAsOne().toArticle()
}

fun NewsDb.insertBookmark(articleData: ArticleData) {
    tableQueries.insertBookmark(
        id = articleData.url,
        title = articleData.title,
        desc = articleData.description,
        text = articleData.text,
        source = articleData.source,
        url = articleData.url,
        image = articleData.image,
        time = articleData.publishedTime,
    )
}

fun NewsDb.deleteBookmark(url: String) {
    tableQueries.deleteBookmark(url)
}
