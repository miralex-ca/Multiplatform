package com.muralex.multiplatform.viewmodel.screens.webviewdetail

import com.muralex.multiplatform.datalayer.functions.getArticleDetail
import com.muralex.multiplatform.datalayer.functions.getBookmarkDetail
import com.muralex.multiplatform.datalayer.functions.getDetail
import com.muralex.multiplatform.viewmodel.Navigation
import com.muralex.multiplatform.viewmodel.ScreenParams
import com.muralex.multiplatform.viewmodel.screens.ScreenInitSettings
import com.muralex.multiplatform.viewmodel.screens.articledetail.DetailSource
import kotlinx.serialization.Serializable

data class ArticleWebviewParams(val url: String) : ScreenParams

fun Navigation.initWebviewDetail(params: ArticleWebviewParams) = ScreenInitSettings (
    title = params.url,
    initState = { ArticleWebviewState(isLoading = true) },
    callOnInit = {

        val articleInfo =  dataRepository.getDetail(params.url)

        stateManager.updateScreen(ArticleWebviewState::class) {
            it.copy(
                isLoading = false,
                articleInfo = ArticleWebviewData(articleInfo._data),
            )
        }
    }
)