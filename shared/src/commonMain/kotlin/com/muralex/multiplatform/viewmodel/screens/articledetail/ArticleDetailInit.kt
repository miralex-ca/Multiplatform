package com.muralex.multiplatform.viewmodel.screens.articledetail

import com.muralex.multiplatform.datalayer.functions.getArticleDetail
import com.muralex.multiplatform.viewmodel.Navigation
import com.muralex.multiplatform.viewmodel.ScreenParams
import com.muralex.multiplatform.viewmodel.screens.ScreenInitSettings
import kotlinx.serialization.Serializable


// INIZIALIZATION settings for this screen
// to understand the initialization behaviour, read the comments in the ScreenInitSettings.kt file

@Serializable
data class ArticleDetailParams(val title: String = "") : ScreenParams

fun Navigation.initArticleDetail(params: ArticleDetailParams) = ScreenInitSettings (
    title = params.title,
    initState = { ArticleDetailState(isLoading = true) },
    callOnInit = {

        val articleInfo = dataRepository.getArticleDetail(params.title)
        // update state, after retrieving data from the repository
        stateManager.updateScreen(ArticleDetailState::class) {
            it.copy(
                isLoading = false,
                articleInfo = articleInfo,
            )
        }
    }
)