package com.muralex.multiplatform.viewmodel.screens

import com.muralex.multiplatform.viewmodel.ScreenIdentifier
import com.muralex.multiplatform.viewmodel.screens.Screen.ArticlesList
import com.muralex.multiplatform.viewmodel.screens.Screen.FavoriteArticlesList
import com.muralex.multiplatform.viewmodel.screens.articleslist.ArticlesListParams
import com.muralex.multiplatform.viewmodel.screens.articleslist.ArticlesListType

// CONFIGURATION SETTINGS

object navigationSettings {
    val homeScreen = Level1Navigation.AllArticles // the start screen should be specified here
    val saveLastLevel1Screen = true
    val alwaysQuitOnHomeScreen = true
}

// LEVEL 1 NAVIGATION OF THE APP

enum class Level1Navigation(
    val screenIdentifier: ScreenIdentifier,
    val rememberVerticalStack: Boolean = false,
) {
    AllArticles(ScreenIdentifier.get(ArticlesList,
        ArticlesListParams(listType = ArticlesListType.ALL)), true),
    FavoriteArticles(ScreenIdentifier.get(FavoriteArticlesList,
        ArticlesListParams(listType = ArticlesListType.FAVORITES)), true),
}