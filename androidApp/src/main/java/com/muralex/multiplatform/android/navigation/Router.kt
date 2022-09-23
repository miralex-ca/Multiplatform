package com.muralex.multiplatform.android.navigation

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.muralex.multiplatform.android.R
import com.muralex.multiplatform.android.ui.BottomData
import com.muralex.multiplatform.android.ui.BottomSheet
import com.muralex.multiplatform.viewmodel.Navigation
import com.muralex.multiplatform.viewmodel.ScreenIdentifier
import com.muralex.multiplatform.viewmodel.screens.Level1Navigation
import com.muralex.multiplatform.viewmodel.screens.Screen
import com.muralex.multiplatform.viewmodel.screens.appsettings.AppSettingsParams
import com.muralex.multiplatform.viewmodel.screens.articleslist.setBookmark
import com.muralex.multiplatform.viewmodel.screens.webviewdetail.ArticleWebviewParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Navigation.Router() {

    val screenUIsStateHolder = rememberSaveableStateHolder()

    OnePane(screenUIsStateHolder)

    screenStatesToRemove.forEach {
        screenUIsStateHolder.removeState(it.URI)
    }

    if (!only1ScreenInBackstack) {
        HandleBackButton()
    }
}

@Composable
fun Navigation.HandleBackButton() {
    BackHandler { // catching the back button to update the DKMPViewModel
        exitScreen()
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun Navigation.OnePane(
    saveableStateHolder: SaveableStateHolder,
) {

    val sheetState = rememberModalBottomSheetState( initialValue = ModalBottomSheetValue.Hidden)

    val coroutineScope = rememberCoroutineScope()

    val bottomData = rememberSaveable {mutableStateOf(BottomData())}

    BackHandler(sheetState.isVisible) {
        closeButtonSheet(coroutineScope, sheetState)
    }

    val shareContext = LocalContext.current

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            BottomSheet(
                bottomData = bottomData.value,
                onCloseClick = {
                    closeButtonSheet(coroutineScope, sheetState)
                },
                onSourceClick = {
                    coroutineScope.launch {
                        closeButtonSheet(coroutineScope, sheetState)
                        delay(100)
                        navigate(Screen.ArticleWebview, ArticleWebviewParams(bottomData.value.title))
                    }
                },
                onShareClick = {
                    share(shareContext, bottomData.value.title)
                },
                onBookmarkClick = {
                    events.setBookmark(bottomData.value.url)
                }
            )
        },
        scrimColor = Color.Transparent,
        sheetShape = RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp),
        sheetBackgroundColor = MaterialTheme.colorScheme.surfaceVariant,
        sheetContentColor = Color.Cyan,
        modifier = Modifier.fillMaxSize(),
        sheetElevation = 20.dp,
    ) {

        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {

            Scaffold(
                topBar = {
                    when (currentScreenIdentifier.screen) {
                        Screen.ArticlesList,
                        Screen.FavoriteArticlesList,
                        -> {
                            TopBar(
                                title = getTitle(currentScreenIdentifier),
                                onSettingsClick = {
                                    navigate(Screen.AppSettings, AppSettingsParams("Settings"))
                                },
                            )
                        }
                        else -> {}
                    }
                },

                content = { innerPadding ->
                    Column(
                        Modifier.padding(innerPadding)
                    ) {
                        saveableStateHolder.SaveableStateProvider(currentScreenIdentifier.URI) {
                            ScreenPicker(
                                screenIdentifier = currentScreenIdentifier,
                                openBottomSheet = { bottomSheetStateData ->

                                    if (bottomSheetStateData.open) {
                                        coroutineScope.launch {
                                            sheetState.animateTo(ModalBottomSheetValue.Expanded)
                                        }
                                    }

                                    bottomData.value = BottomData(
                                        title = bottomSheetStateData.data?.title ?: "",
                                        text = bottomSheetStateData.data?.description ?: "",
                                        image = bottomSheetStateData.data?.image ?: "",
                                        url = bottomSheetStateData.data?.url ?: "",
                                        isFavorite = bottomSheetStateData.isFavorite
                                    )

                                }

                            )
                        }
                    }
                },
                bottomBar = {
                    if (currentScreenIdentifier.screen.navigationLevel == 1) Level1BottomBar(
                        currentScreenIdentifier)
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
private fun closeButtonSheet(
    coroutineScope: CoroutineScope,
    sheetState: ModalBottomSheetState,
) {
    coroutineScope.launch { sheetState.hide() }
}


@Composable
fun Navigation.Level1BottomBar(
    selectedTab: ScreenIdentifier,
) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 1.dp
    ) {
        NavigationBarItem(
            icon = {
                Icon(imageVector = Icons.Outlined.Newspaper,
                    contentDescription = stringResource(id = R.string.tab_home))
            },
            label = { Text(stringResource(id = R.string.tab_home)) },
            selected = selectedTab.URI == Level1Navigation.AllArticles.screenIdentifier.URI,
            onClick = { navigateByLevel1Menu(Level1Navigation.AllArticles) }
        )
        NavigationBarItem(
            icon = {
                Icon(imageVector = Icons.Outlined.Bookmarks,
                    contentDescription = stringResource(id = R.string.tab_fav))
            },
            label = { Text(stringResource(id = R.string.tab_fav)) },
            selected = selectedTab.URI == Level1Navigation.FavoriteArticles.screenIdentifier.URI,
            onClick = { navigateByLevel1Menu(Level1Navigation.FavoriteArticles) }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onSettingsClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(title = {
        Text(text = title,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth())
    },
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    )
}















