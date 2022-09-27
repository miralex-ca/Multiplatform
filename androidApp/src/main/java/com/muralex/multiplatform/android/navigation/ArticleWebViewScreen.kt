package com.muralex.multiplatform.android.navigation

import android.annotation.SuppressLint
import android.graphics.Bitmap

import android.util.Log
import android.webkit.URLUtil
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.web.*
import com.muralex.multiplatform.android.R
import com.muralex.multiplatform.viewmodel.screens.webviewdetail.ArticleWebviewData
import com.muralex.multiplatform.viewmodel.screens.webviewdetail.ArticleWebviewState
import kotlinx.coroutines.delay

@Composable
fun ArticleWebViewScreen(
    articleState: ArticleWebviewState,
    screenData: MutableState<ScreenData>,
) {
    screenData.value = ScreenData(
        title = articleState.articleInfo._data.source,
        url = articleState.articleInfo._data.url
    )
    Column {
        ArticleWebViewContent(articleState.articleInfo)
    }
}

@Composable
private fun ArticleWebViewContent(item: ArticleWebviewData) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        LoadWebUrl(item._data.url)
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LoadWebUrl(url: String) {

    val state = rememberWebViewState(url = url)
    val navigator = rememberWebViewNavigator()
    val loaderVisibility = remember { mutableStateOf(true)}

    if (  URLUtil.isValidUrl(url) ) {
        WebClientBox(
            visibility = loaderVisibility,
            state = state,
            navigator = navigator
        )
    } else {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            loaderVisibility.value = false
            Text(
                text = stringResource(id = R.string.invalid_url),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .alpha(0.6f)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
    }


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        if (loaderVisibility.value) {
            CircularProgressIndicator(
            )
        }
    }
}

@Composable
private fun WebClientBox(
    visibility: MutableState<Boolean>,
    state: WebViewState,
    navigator: WebViewNavigator,
) {
    val webClient = remember {
        object : AccompanistWebViewClient() {
            override fun onPageStarted(
                view: WebView?,
                url: String?,
                favicon: Bitmap?,
            ) {
                super.onPageStarted(view, url, favicon)
                Log.d("Accompanist WebView", "Page started loading for $url")
            }

            override fun onPageCommitVisible(view: WebView?, url: String?) {
                super.onPageCommitVisible(view, url)
                visibility.value = false
            }
        }
    }

    val build = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(500)
        build.value = true
    }

    if (build.value) {
        WebView(
            state = state,
            navigator = navigator,
            onCreated = { webView ->
                webView.settings.javaScriptEnabled = true
            },
            client = webClient
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  ArticleWebViewTopBar(
    title : String = "",
    onBackClick: () -> Unit,
    onMenuShareItemSelected: () -> Unit = {},
    onMenuOpenItemSelected: () -> Unit = {},
){
    var mDisplayMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = {Text(text = title, fontSize = 18.sp)},
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            OpenBrowserToolbarIcon(
                onClick = onMenuOpenItemSelected
            )

            IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
            }

            DropdownMenu(
                expanded = mDisplayMenu,
                onDismissRequest = { mDisplayMenu = false },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .defaultMinSize(180.dp)
            ) {
                DropdownMenuItem(
                    onClick = {
                        onMenuShareItemSelected.invoke()
                        mDisplayMenu = false
                    },
                    text = {
                        Text(text = stringResource(id = R.string.menu_share),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                )

                DropdownMenuItem(
                    onClick = {
                        onMenuOpenItemSelected.invoke()
                        mDisplayMenu = false
                    },
                    text = {
                        Text(text = stringResource(id = R.string.open_browser),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                )
            }
        }
    )
}

@Composable
fun OpenBrowserToolbarIcon(
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Outlined.Logout,
            contentDescription = stringResource(id = R.string.open_browser),
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}