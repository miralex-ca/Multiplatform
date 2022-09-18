package com.muralex.multiplatform.viewmodel.screens.webviewdetail

import com.muralex.multiplatform.datalayer.functions.getArticleDetail
import com.muralex.multiplatform.viewmodel.Navigation
import com.muralex.multiplatform.viewmodel.ScreenParams
import com.muralex.multiplatform.viewmodel.screens.ScreenInitSettings
import kotlinx.serialization.Serializable


// INIZIALIZATION settings for this screen
// to understand the initialization behaviour, read the comments in the ScreenInitSettings.kt file

@Serializable // Note: ScreenParams should always be set as Serializable
data class ArticleWebviewParams(val title: String) : ScreenParams

fun Navigation.initWebviewDetail(params: ArticleWebviewParams) = ScreenInitSettings (
    title = params.title,
    initState = { ArticleWebviewState(isLoading = true) },
    callOnInit = {

        val articleInfo = dataRepository.getArticleDetail(params.title)
        // update state, after retrieving data from the repository
        stateManager.updateScreen(ArticleWebviewState::class) {
            it.copy(
                isLoading = false,
                articleInfo = ArticleWebviewData(articleInfo._data),
            )
        }
    }
)