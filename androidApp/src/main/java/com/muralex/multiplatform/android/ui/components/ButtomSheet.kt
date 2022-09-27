package com.muralex.multiplatform.android.ui.components

import android.os.Parcelable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.twotone.Bookmark
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.muralex.multiplatform.android.R
import com.muralex.multiplatform.datalayer.objects.ArticleData
import kotlinx.parcelize.Parcelize

@Parcelize
data class BottomData(
    val title: String = String(),
    val isFavorite: Boolean = false,
    val text: String = String(),
    val image: String = String(),
    val url: String = String(),
) : Parcelable


@Composable
fun BottomSheet(
    bottomData: BottomData = BottomData(),
    onShareClick: () -> Unit = {},
    onSourceClick: () -> Unit = {},
    onBookmarkClick: () -> Unit = {},
    onCloseClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(32.dp)
            .verticalScroll(rememberScrollState()),
    ) {

        Column(
            modifier = Modifier
                .defaultMinSize(minHeight = 200.dp)
        ) {

            if (bottomData.image.isNotEmpty()) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(bottomData.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = bottomData.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(190.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            Text(
                text = bottomData.title,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = bottomData.text,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(25.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            ButtomSheetButton(
                onClick = onShareClick,
                text = stringResource(id = R.string.btm_sheet_btn_share),
                icon = Icons.Outlined.Share,
            )


            ButtomSheetButton(
                onClick = onBookmarkClick,
                text = stringResource(id = R.string.btm_sheet_btn_bookmark),
                icon = if (bottomData.isFavorite) Icons.TwoTone.Bookmark
                        else Icons.Outlined.BookmarkAdd
            )


            ButtomSheetButton(
                onClick = onSourceClick,
                text = stringResource(id = R.string.btm_sheet_btn_source),
                icon = Icons.Outlined.OpenInNew,
            )

            ButtomSheetButton(
                onClick = onCloseClick,
                text = stringResource(id = R.string.btm_sheet_btn_close),
                icon = Icons.Outlined.Cancel,
            )
        }
    }
}

@Composable
private fun ButtomSheetButton(
    onClick: () -> Unit = {},
    text: String = "",
    icon: ImageVector,
) {
    TextButton(
        onClick = onClick,
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}