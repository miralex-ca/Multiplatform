package eu.baroncelli.dkmpsample.composables.screens.countrieslist

import android.annotation.SuppressLint
import android.graphics.Bitmap

import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.OpenInBrowser
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Black

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState
import com.muralex.multiplatform.android.R
import com.muralex.multiplatform.android.navigation.LoadingScreen
import com.muralex.multiplatform.viewmodel.screens.articledetail.ArticleDetail
import com.muralex.multiplatform.viewmodel.screens.articledetail.ArticleDetailState
import com.muralex.multiplatform.viewmodel.screens.webviewdetail.ArticleWebviewData
import com.muralex.multiplatform.viewmodel.screens.webviewdetail.ArticleWebviewState
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun ArticleWebViewScreen(
    articleState: ArticleWebviewState,
    exitScreen: ()-> Unit
) {

    Column {
        ArticleWebViewTopBar(
            onBackClick = exitScreen
        )
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

    val visibility = remember { mutableStateOf(true)}

    LaunchedEffect(Unit) {
        delay(5.seconds)

    }

    val webClient = remember {
        object : AccompanistWebViewClient() {
            override fun onPageStarted(
                view: WebView?,
                url: String?,
                favicon: Bitmap?
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

    WebView(
        state = state,
        modifier = Modifier
            .background(color = Color(0xff0f9d58))
            //.fillMaxHeight()
                ,
        navigator = navigator,
        onCreated = { webView ->
            webView.settings.javaScriptEnabled = true
        },

        client = webClient
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        if (visibility.value) {
            CircularProgressIndicator(
            )
        }

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
                onClick = {}
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
                        Text(text = stringResource(id = R.string.menu_open_source),
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
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}