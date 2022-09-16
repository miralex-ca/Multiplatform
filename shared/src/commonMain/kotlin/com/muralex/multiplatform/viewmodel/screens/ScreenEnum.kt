package com.muralex.multiplatform.viewmodel.screens

import com.muralex.multiplatform.viewmodel.Navigation
import com.muralex.multiplatform.viewmodel.ScreenIdentifier
import com.muralex.multiplatform.viewmodel.screens.articledetail.initArticleDetail
import com.muralex.multiplatform.viewmodel.screens.articleslist.initArticlesList
import com.muralex.multiplatform.viewmodel.screens.favoriteslist.initFavoriteList


// DEFINITION OF ALL SCREENS IN THE APP

enum class Screen(
    val asString: String,
    val navigationLevel : Int = 1,
    val initSettings: Navigation.(ScreenIdentifier) -> ScreenInitSettings,
    val stackableInstances : Boolean = false,
) {
    ArticlesList("articleslist", 1, { initArticlesList(it.params()) }, true),
    ArticleDetail("article", 2, { initArticleDetail(it.params()) }),
    FavoriteArticlesList("favoritelist", 1, { initFavoriteList() }, true),
}