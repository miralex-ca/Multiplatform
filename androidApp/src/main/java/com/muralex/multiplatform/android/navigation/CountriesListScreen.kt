package com.muralex.multiplatform.android.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.muralex.multiplatform.android.util.formatToDate
import com.muralex.multiplatform.viewmodel.screens.articleslist.ArticlesListItem
import com.muralex.multiplatform.viewmodel.screens.articleslist.ArticlesListState
import com.muralex.multiplatform.viewmodel.screens.articleslist.BottomSheetStateData

@Composable
fun ArticlesListScreen(
    countriesListState: ArticlesListState,
    openBottomSheet: (BottomSheetStateData) -> Unit,
    onListItemClick: (String) -> Unit,
    onBottomSheetOpen: () -> Unit,
) {
    if (countriesListState.isLoading) {
        LoadingScreen()
    } else {

        if (countriesListState.bottomSheetStateData.open) {
            openBottomSheet.invoke(countriesListState.bottomSheetStateData)
            onBottomSheetOpen.invoke()
        } else {
            openBottomSheet.invoke(countriesListState.bottomSheetStateData)
        }

        if (countriesListState.articlesListItems.isEmpty()) {

            Text(
                text = "empty list",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(top = 30.dp)
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
                    itemContent = { item ->
                        ArticleListItem(
                            item = item,
                            onItemClick  = {
                                onListItemClick(item._data.url)
                            },
                        )
                    })
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleListItem(
    item: ArticlesListItem,
    onItemClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp),
        onClick = onItemClick,

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)

        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {

                if (item._data.image.isNotEmpty()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item._data.image)
                            .crossfade(true)
                            .build(),
                        contentDescription = item.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(85.dp)
                            .width(85.dp)
                            .clip(shape = RoundedCornerShape(8.dp))
                    )
                }

                Column(
                    modifier = Modifier.padding(start = 14.dp, end = 6.dp)
                ) {
                    Text(
                        text = item.name,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 3
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = item._data.publishedTime.formatToDate(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Light,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}