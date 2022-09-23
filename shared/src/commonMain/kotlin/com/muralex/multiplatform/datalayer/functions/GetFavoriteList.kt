package com.muralex.multiplatform.datalayer.functions

import com.muralex.multiplatform.datalayer.Repository
import com.muralex.multiplatform.datalayer.objects.ArticleData
import com.muralex.multiplatform.datalayer.sources.localdb.checkBookmark
import com.muralex.multiplatform.datalayer.sources.localdb.deleteBookmark
import com.muralex.multiplatform.datalayer.sources.localdb.getBookmarksList
import com.muralex.multiplatform.datalayer.sources.localdb.insertBookmark
import com.muralex.multiplatform.viewmodel.screens.favoriteslist.FavoriteListItem

suspend fun Repository.getFavoriteListData(): List<FavoriteListItem> = withRepoContext {
     localDb.getBookmarksList().map {
         FavoriteListItem(it)
    }.asReversed()
}

suspend fun Repository.checkBookmark(url: String): Boolean = withRepoContext {
    localDb.checkBookmark(url)
}

suspend fun Repository.insertBookmark(article: ArticleData) = withRepoContext {
    localDb.insertBookmark(article)
}

suspend fun Repository.insertBookmarkByUrl(url: String) = withRepoContext {
    runtimeCache.articlesList.find { it.url == url  }?.let {
        localDb.insertBookmark(it)
    }
}

suspend fun Repository.deleteBookmark(url: String) = withRepoContext {
    localDb.deleteBookmark(url)
}