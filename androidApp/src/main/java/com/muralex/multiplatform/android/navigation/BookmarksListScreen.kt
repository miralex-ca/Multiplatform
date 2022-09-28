package com.muralex.multiplatform.android.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkRemove
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.muralex.multiplatform.android.R
import com.muralex.multiplatform.android.util.formatToDate
import com.muralex.multiplatform.viewmodel.screens.favoriteslist.FavoriteListItem
import com.muralex.multiplatform.viewmodel.screens.favoriteslist.FavoriteListState

@Composable
fun FavoriteListScreen(
    screenData: MutableState<ScreenData>,
    countriesListState: FavoriteListState,
    onListItemClick: (String) -> Unit,
    onBookMarkRemove: (String) -> Unit,
) {
    screenData.value = ScreenData(
        title = stringResource(id = R.string.tab_fav)
    )

    if (!countriesListState.isLoading) {
        if (countriesListState.articlesListItems.isEmpty()) {
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "No bookmarks added yet",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(top = 30.dp)
                    .alpha(0.7f)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
        } else {

            LazyColumn(
                contentPadding = PaddingValues(vertical = 10.dp),
            ) {

                items(
                    items = countriesListState.articlesListItems,
                    key = { list: FavoriteListItem -> list._data.url },
                    itemContent = { item ->
                        FavoriteListItem(
                            onBookMarkRemove = { onBookMarkRemove.invoke(item._data.url) },
                            item = item,
                            onItemClick = { onListItemClick(item._data.url) },
                        )
                    })
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteListItem(
    onBookMarkRemove: (String) -> Unit,
    item: FavoriteListItem,
    onItemClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp),
        onClick = onItemClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row(modifier = Modifier.fillMaxWidth()) {

                if (item._data.image.isNotEmpty()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item._data.image)
                            .crossfade(true)
                            .build(),
                        contentDescription = item.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(75.dp)
                            .width(85.dp)
                            .clip(shape = RoundedCornerShape(8.dp))

                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }

                Column(
                    modifier = Modifier.padding(end = 6.dp)
                ) {
                    Text(
                        text = item.name,
                        fontSize = 17.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "${item._data.source.uppercase()}  –  ${item._data.publishedTime.formatToDate()}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp
                    )

                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = item.desc,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 3
            )
            Spacer(Modifier.height(6.dp))
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun SwipeToDismissBookmark(
    onBookMarkRemove: (String) -> Unit,
    item: FavoriteListItem,
    onItemClick: () -> Unit,
) {

    val dismissState = rememberDismissState(initialValue = DismissValue.Default)

    if (dismissState.isDismissed(DismissDirection.EndToStart)) {
        onBookMarkRemove.invoke(item._data.url)
    }

    SwipeToDismiss(
        state = dismissState,
        background = {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .padding(8.dp)


            ) {
                Row(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .alpha(0.6f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Delete bookmark",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(5.dp))

                    Icon(
                        imageVector = Icons.Outlined.BookmarkRemove,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .size(30.dp)

                    )
                    Spacer(modifier = Modifier.width(30.dp))
                }
            }

        },
        dismissContent = {
             // swipe to dismiss not working
        },
        dismissThresholds = { direction ->
            FractionalThreshold(0.5f)
        },
        directions = setOf(DismissDirection.EndToStart),
    )


}
