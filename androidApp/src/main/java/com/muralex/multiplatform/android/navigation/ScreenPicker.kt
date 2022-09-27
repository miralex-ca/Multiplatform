package com.muralex.multiplatform.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.muralex.multiplatform.viewmodel.Navigation
import com.muralex.multiplatform.viewmodel.ScreenIdentifier
import com.muralex.multiplatform.viewmodel.screens.Screen
import com.muralex.multiplatform.viewmodel.screens.appsettings.setApiSourceIndex
import com.muralex.multiplatform.viewmodel.screens.appsettings.setOpenFromListIndex
import com.muralex.multiplatform.viewmodel.screens.appsettings.setSourceCountryByIndex
import com.muralex.multiplatform.viewmodel.screens.appsettings.setThemeModeByIndex
import com.muralex.multiplatform.viewmodel.screens.articledetail.ArticleDetailParams
import com.muralex.multiplatform.viewmodel.screens.articledetail.setArticleBookmark
import com.muralex.multiplatform.viewmodel.screens.articleslist.BottomSheetStateData
import com.muralex.multiplatform.viewmodel.screens.articleslist.openBottomSheet
import com.muralex.multiplatform.viewmodel.screens.articleslist.resetBottomSheet
import com.muralex.multiplatform.viewmodel.screens.favoriteslist.removeFavorite
import com.muralex.multiplatform.viewmodel.screens.webviewdetail.ArticleWebviewParams

@Composable
fun Navigation.ScreenPicker(
    screenIdentifier: ScreenIdentifier,
    openBottomSheet: (BottomSheetStateData) -> Unit,
    screenData: MutableState<ScreenData>,
) {

    when (screenIdentifier.screen) {
        Screen.FavoriteArticlesList ->
            FavoriteListScreen(
                screenData = screenData,
                countriesListState = stateProvider.get(screenIdentifier),
                onListItemClick = { url ->
                    navigate(Screen.ArticleDetail, ArticleDetailParams(url))
                },
                onBookMarkRemove = { url: String ->
                    events.removeFavorite(url)
                }
            )

        Screen.ArticleDetail ->
            ArticleDetailScreen(
                articleState = stateProvider.get(screenIdentifier),
                openInBrowser = { url ->
                    navigate(Screen.ArticleWebview, ArticleWebviewParams(url))
                },
                exitScreen = { exitScreen(triggerRecomposition = true) },
                onBookmarkClick = {article ->
                    events.setArticleBookmark(article)
                }
            )

        Screen.ArticleWebview ->
            ArticleWebViewScreen(
                screenData = screenData,
                articleState = stateProvider.get(screenIdentifier),
            )

        Screen.AppSettings ->

            AppSettingsScreen(
                pageState = stateProvider.get(screenIdentifier),
                exitScreen = { exitScreen(triggerRecomposition = true) },
                selectThemeMode = {events.setThemeModeByIndex(it)},
                selectCountry = {events.setSourceCountryByIndex(it)},
                selectOpenFromList = {events.setOpenFromListIndex(it)},
                selectApiSource = {events.setApiSourceIndex(it)},
            )

        else -> ArticlesListScreen(
            screenData = screenData,
            openBottomSheet = openBottomSheet,
            countriesListState = stateProvider.get(screenIdentifier),
            onListItemClick = { url ->
                events.openBottomSheet(url)
            },
            onBottomSheetOpen = {
                events.resetBottomSheet()
            }
        )
    }
}
