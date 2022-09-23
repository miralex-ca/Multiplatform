package com.muralex.multiplatform.viewmodel.screens.articledetail

import com.muralex.multiplatform.datalayer.functions.checkBookmark
import com.muralex.multiplatform.datalayer.functions.deleteBookmark
import com.muralex.multiplatform.datalayer.functions.insertBookmark
import com.muralex.multiplatform.datalayer.functions.insertBookmarkByUrl
import com.muralex.multiplatform.datalayer.objects.ArticleData
import com.muralex.multiplatform.viewmodel.Events


fun Events.setArticleBookmark(article: ArticleData) = screenCoroutine {

    val isBookmark = dataRepository.checkBookmark(article.url)
    if (isBookmark) dataRepository.deleteBookmark(article.url)
    else dataRepository.insertBookmark(article)

    val result = dataRepository.checkBookmark(article.url)

    stateManager.updateScreen(ArticleDetailState::class) {
        it.copy(articleInfo = it.articleInfo.copy(isFavorite = result))
    }
}


