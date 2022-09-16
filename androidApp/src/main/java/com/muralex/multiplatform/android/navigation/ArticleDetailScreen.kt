package eu.baroncelli.dkmpsample.composables.screens.countrieslist

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.request.ImageRequest
import com.muralex.multiplatform.viewmodel.screens.articleslist.ArticlesListItem
import com.muralex.multiplatform.viewmodel.screens.articleslist.ArticlesListState
import coil.compose.AsyncImage
import com.muralex.multiplatform.android.navigation.LoadingScreen
import com.muralex.multiplatform.viewmodel.screens.articledetail.ArticleDetail
import com.muralex.multiplatform.viewmodel.screens.articledetail.ArticleDetailState
import kotlinx.coroutines.delay

@Composable
fun ArticleDetailScreen(
    countriesListState: ArticleDetailState,
) {
    if (countriesListState.isLoading) {
        LoadingScreen()
    } else {

        ArticleDetailItem(
            item = countriesListState.articleInfo,
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailItem(
    item: ArticleDetail,
    favorite: Boolean = true
) {

    Box(
        modifier= Modifier.fillMaxSize()
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

                    Spacer(Modifier.height(30.dp))
                }
            }
        }


    }



}