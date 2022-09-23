package com.muralex.multiplatform.android.navigation

import androidx.compose.runtime.Composable
import com.muralex.multiplatform.viewmodel.Navigation
import com.muralex.multiplatform.viewmodel.ScreenIdentifier
import com.muralex.multiplatform.viewmodel.screens.Screen
import com.muralex.multiplatform.viewmodel.screens.articledetail.ArticleDetailParams
import com.muralex.multiplatform.viewmodel.screens.articledetail.setArticleBookmark
import com.muralex.multiplatform.viewmodel.screens.articleslist.BottomSheetStateData
import com.muralex.multiplatform.viewmodel.screens.articleslist.openBottomSheet
import com.muralex.multiplatform.viewmodel.screens.articleslist.resetBottomSheet
import com.muralex.multiplatform.viewmodel.screens.webviewdetail.ArticleWebviewParams

@Composable
fun Navigation.ScreenPicker(
    screenIdentifier: ScreenIdentifier,
    openBottomSheet: (BottomSheetStateData) -> Unit,
   // bottomData: MutableState<BottomData>,
) {

    when (screenIdentifier.screen) {
        Screen.FavoriteArticlesList ->
            FavoriteListScreen(
                countriesListState = stateProvider.get(screenIdentifier),
                onListItemClick = { url ->
                    navigate(Screen.ArticleDetail, ArticleDetailParams(url))
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
                articleState = stateProvider.get(screenIdentifier),
                exitScreen = { exitScreen(triggerRecomposition = true) }
            )

        Screen.AppSettings ->
            AppSettingsScreen(
                pageState = stateProvider.get(screenIdentifier),
                exitScreen = { exitScreen(triggerRecomposition = true) }
            )

        else -> ArticlesListScreen(
            openBottomSheet = openBottomSheet,
            countriesListState = stateProvider.get(screenIdentifier),
            onListItemClick = { url ->
                events.openBottomSheet(url)
            },
            onBottomSheetOpen = {
                events.resetBottomSheet()
            },
            //bottomData = bottomData
        )
    }
}
