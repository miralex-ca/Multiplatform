package com.muralex.multiplatform.viewmodel.screens.articleslist

import com.muralex.multiplatform.datalayer.functions.getArticlesListData
import com.muralex.multiplatform.viewmodel.Navigation
import com.muralex.multiplatform.viewmodel.ScreenParams
import com.muralex.multiplatform.viewmodel.screens.ScreenInitSettings
import kotlinx.serialization.Serializable

@Serializable // Note: ScreenParams should always be set as Serializable
data class ArticlesListParams(val listType: ArticlesListType) : ScreenParams

fun Navigation.initArticlesList(params: ArticlesListParams) = ScreenInitSettings (
    title = "News: Canada",
    initState = { ArticlesListState(isLoading = true) },
    callOnInit = {

        val listData = dataRepository.getArticlesListData()
        stateManager.updateScreen(ArticlesListState::class) {
            it.copy(
                isLoading = false,
                articlesListItems = listData.map { ArticlesListItem(it) }
            )
        }
    },
    reinitOnEachNavigation = true, // in this way favourites can refresh
)