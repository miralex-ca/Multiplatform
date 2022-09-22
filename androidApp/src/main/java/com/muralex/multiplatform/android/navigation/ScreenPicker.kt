package com.muralex.multiplatform.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.muralex.multiplatform.android.ui.BottomData
import com.muralex.multiplatform.datalayer.objects.ArticleData
import com.muralex.multiplatform.viewmodel.Navigation
import com.muralex.multiplatform.viewmodel.ScreenIdentifier
import com.muralex.multiplatform.viewmodel.screens.Screen
import com.muralex.multiplatform.viewmodel.screens.articledetail.ArticleDetailParams
import com.muralex.multiplatform.viewmodel.screens.articleslist.openBottomSheet
import com.muralex.multiplatform.viewmodel.screens.articleslist.resetBottomSheet
import com.muralex.multiplatform.viewmodel.screens.webviewdetail.ArticleWebviewParams

@Composable
fun Navigation.ScreenPicker(
    screenIdentifier: ScreenIdentifier,
    openBottomSheet: (ArticleData?) -> Unit,
    bottomData: MutableState<BottomData>,
) {

    when (screenIdentifier.screen) {
        Screen.FavoriteArticlesList ->
            FavoriteListScreen(
                countriesListState = stateProvider.get(screenIdentifier),
                onListItemClick = {
                    navigate(Screen.ArticleDetail, ArticleDetailParams())
                }
            )

        Screen.ArticleDetail ->
            ArticleDetailScreen(
                articleState = stateProvider.get(screenIdentifier),
                openInBrowser = { title ->
                    navigate(Screen.ArticleWebview, ArticleWebviewParams(title))
                },
                exitScreen = { exitScreen(triggerRecomposition = true) }
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
            onListItemClick = { title ->
                events.openBottomSheet(title)
            },
            onBottomSheetOpen = {
                events.resetBottomSheet()
            },
            bottomData = bottomData
        )
    }
}
