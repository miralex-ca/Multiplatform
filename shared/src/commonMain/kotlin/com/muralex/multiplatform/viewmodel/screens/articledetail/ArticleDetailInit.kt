package com.muralex.multiplatform.viewmodel.screens.articledetail

import com.muralex.multiplatform.datalayer.functions.getArticleDetail
import com.muralex.multiplatform.datalayer.functions.getBookmarkDetail
import com.muralex.multiplatform.viewmodel.Navigation
import com.muralex.multiplatform.viewmodel.ScreenParams
import com.muralex.multiplatform.viewmodel.screens.ScreenInitSettings
import kotlinx.serialization.Serializable


@Serializable
data class ArticleDetailParams(val url: String = "", val source: String = DetailSource.BOOKMARKS.name) : ScreenParams

fun Navigation.initArticleDetail(params: ArticleDetailParams) = ScreenInitSettings (
    title = params.url,
    initState = { ArticleDetailState(isLoading = true) },
    callOnInit = {

        val articleInfo = if (params.source.uppercase() == DetailSource.BOOKMARKS.name) {
            dataRepository.getBookmarkDetail(params.url)
        } else {
             dataRepository.getArticleDetail(params.url)
        }

        stateManager.updateScreen(ArticleDetailState::class) {
            it.copy(
                isLoading = false,
                articleInfo = articleInfo,
            )
        }
    }
)

enum class DetailSource {
    BOOKMARKS,
}