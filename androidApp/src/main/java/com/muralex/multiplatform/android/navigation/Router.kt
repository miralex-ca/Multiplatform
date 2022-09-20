package eu.baroncelli.dkmpsample.composables.navigation

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ShareCompat
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import com.muralex.multiplatform.android.R
import com.muralex.multiplatform.viewmodel.Navigation
import com.muralex.multiplatform.viewmodel.ScreenIdentifier
import com.muralex.multiplatform.viewmodel.screens.Level1Navigation
import com.muralex.multiplatform.viewmodel.screens.Screen
import com.muralex.multiplatform.viewmodel.screens.appsettings.AppSettingsParams
import com.muralex.multiplatform.viewmodel.screens.articledetail.ArticleDetail
import com.muralex.multiplatform.viewmodel.screens.webviewdetail.ArticleWebviewParams

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
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun Navigation.OnePane(
    saveableStateHolder: SaveableStateHolder,
) {


    val bottomSheetNavigator = rememberBottomSheetNavigator()
    ModalBottomSheetLayout(bottomSheetNavigator) {

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
                                }
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
                            ScreenPicker(currentScreenIdentifier)
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






