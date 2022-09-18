package com.muralex.multiplatform.android.navigation

import android.content.Context
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.OpenInNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ShareCompat
import coil.request.ImageRequest
import coil.compose.AsyncImage
import com.muralex.multiplatform.android.R
import com.muralex.multiplatform.viewmodel.screens.articledetail.ArticleDetail
import com.muralex.multiplatform.viewmodel.screens.articledetail.ArticleDetailState

@Composable
fun ArticleDetailScreen(
    articleState: ArticleDetailState,
    openInBrowser: () -> Unit = {},
    exitScreen: () -> Unit,
) {

    ArticleDetailItem(
        item = articleState.articleInfo,
        openInBrowser = openInBrowser,
        exitScreen = exitScreen,
    )
}

@Composable
fun ArticleDetailItem(
    item: ArticleDetail,
    openInBrowser: () -> Unit = {},
    exitScreen: () -> Unit,
) {

    val shareContext = LocalContext.current

    Column {
        TopBarLevel2(
            isFavorite = item.isFavorite,
            onBackClick = exitScreen,
            onMenuShareItemSelected = { share(shareContext, item._data.url) },
            onMenuOpenItemSelected = openInBrowser,
        )

        val state = remember {
            MutableTransitionState(false).apply {
                targetState = true
            }
        }

        AnimatedVisibility(
            visibleState = state,
            enter = fadeIn( animationSpec = tween(200)),
            exit = fadeOut()
        ) {
            DetailContent(
                item = item,
                openInBrowser =  openInBrowser,
            )
        }
    }
}

@Composable
private fun DetailContent(
    item: ArticleDetail,
    openInBrowser: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.TopCenter) {

        Card(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item._data.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = item._data.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(220.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(8.dp))
                )

                Column(
                    modifier = Modifier.padding(start = 6.dp, end = 6.dp)
                ) {
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = item._data.title,
                        fontSize = 20.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 3
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "Date: 22/22/22",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text =
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras vulputate urna in neque facilisis, vitae fermentum est sollicitudin. Mauris nec maximus enim, sed aliquam ante. Nunc purus dolor, sollicitudin et aliquet non, commodo ac lorem. ",
                        fontWeight = FontWeight.Light,
                        fontSize = 15.sp
                    )
                    Spacer(Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {

                        TextButton(
                            onClick = openInBrowser,
                        ) {
                            Text(
                                text = stringResource(id = R.string.btn_open_source)

                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Icon(
                                imageVector = Icons.Outlined.OpenInNew,
                                contentDescription = null,
                                modifier = Modifier.size(ButtonDefaults.IconSize)
                            )
                        }

                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarLevel2(
    title: String = "",
    isFavorite: Boolean = false,
    onBackClick: () -> Unit,
    onMenuShareItemSelected: () -> Unit = {},
    onMenuOpenItemSelected: () -> Unit = {},
    onBookmarkClicked: () -> Unit = {},
) {
    var mDisplayMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = title, fontSize = 18.sp) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        },
        actions = {

            BookmarkToolbarIcon(
                isFavorite = isFavorite,
                onClick = onBookmarkClicked
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
fun BookmarkToolbarIcon(
    isFavorite: Boolean = false,
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector =
            if (isFavorite) Icons.Filled.Bookmark
            else Icons.Outlined.BookmarkBorder,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}


fun share(shareContext: Context, url: String) {
    ShareCompat.IntentBuilder(shareContext)
        .setType("text/plain")
        .setChooserTitle(shareContext.getString(R.string.text_share))
        .setText(shareContext.getString(R.string.share_url) + url)
        .startChooser()
}