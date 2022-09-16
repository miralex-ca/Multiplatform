package eu.baroncelli.dkmpsample.composables.navigation

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Videocam
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
import com.muralex.multiplatform.android.R
import com.muralex.multiplatform.viewmodel.Navigation
import com.muralex.multiplatform.viewmodel.ScreenIdentifier
import com.muralex.multiplatform.viewmodel.screens.Level1Navigation
import com.muralex.multiplatform.viewmodel.screens.Screen

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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation.OnePane(
    saveableStateHolder: SaveableStateHolder,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.inverseOnSurface)
    ) {

        Scaffold(
            topBar = {
                when (currentScreenIdentifier.screen) {
                    Screen.ArticleDetail -> {
                        TopBarLevel2(
                            title = "Source",
                            onBack = {
                                exitScreen(triggerRecomposition = true)
                            }
                        )
                    }
                    else -> {
                        TopBar(title = getTitle(currentScreenIdentifier))
                    }
                }

            },

            content = { innerPadding ->
                Column(Modifier.padding(innerPadding)) {
                    saveableStateHolder.SaveableStateProvider(currentScreenIdentifier.URI) {
                        ScreenPicker(currentScreenIdentifier)
                    }
                }
            },
            bottomBar = {
                if (currentScreenIdentifier.screen.navigationLevel == 1)  Level1BottomBar(currentScreenIdentifier)
            }
        )
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
    title : String
){
    TopAppBar(title = {
        Text(text = title, fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
    })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarLevel2(
    title : String,
    onBack: () -> Unit
){
    TopAppBar(
        title = {Text(text = title, fontSize = 18.sp)},
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        }

    )
}



