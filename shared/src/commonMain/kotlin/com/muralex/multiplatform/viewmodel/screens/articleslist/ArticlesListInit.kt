package com.muralex.multiplatform.viewmodel.screens.articleslist

import com.muralex.multiplatform.datalayer.functions.getArticlesListData
import com.muralex.multiplatform.viewmodel.Navigation
import com.muralex.multiplatform.viewmodel.ScreenParams
import com.muralex.multiplatform.viewmodel.screens.ScreenInitSettings
import kotlinx.serialization.Serializable

// INIZIALIZATION settings for this screen
// this is what should be implemented:
// - a data class implementing the ScreenParams interface, which defines the parameters to the passed to the screen
// - Navigation extension function taking the ScreenParams class as an argument, return the ScreenInitSettings for this screen
// to understand the initialization behaviour, read the comments in the ScreenInitSettings.kt file

@Serializable // Note: ScreenParams should always be set as Serializable
data class ArticlesListParams(val listType: ArticlesListType) : ScreenParams

fun Navigation.initArticlesList(params: ArticlesListParams) = ScreenInitSettings (
    title = "National Headlines ",
    initState = { ArticlesListState(isLoading = true) },
    callOnInit = {
        val listData = dataRepository.getArticlesListData()

        // update state, after retrieving data from the repository
        stateManager.updateScreen(ArticlesListState::class) {
            it.copy(
                isLoading = false,
                articlesListItems = listData,
            )
        }
    },
    reinitOnEachNavigation = true, // in this way favourites can refresh
)