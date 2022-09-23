package com.muralex.multiplatform.viewmodel.screens.articleslist

import com.muralex.multiplatform.datalayer.functions.checkBookmark
import com.muralex.multiplatform.datalayer.functions.deleteBookmark
import com.muralex.multiplatform.datalayer.functions.getArticleDetail
import com.muralex.multiplatform.datalayer.functions.insertBookmarkByUrl
import com.muralex.multiplatform.viewmodel.Events

/********** EVENT functions, called directly by the UI layer **********/

fun Events.setBookmark(url: String) = screenCoroutine {

    val isBookmark = dataRepository.checkBookmark(url)
    if (isBookmark) dataRepository.deleteBookmark(url)
    else dataRepository.insertBookmarkByUrl(url)

    val result = dataRepository.checkBookmark(url)

    stateManager.updateScreen(ArticlesListState::class) {
        it.copy(bottomSheetStateData = it.bottomSheetStateData.copy(isFavorite = result))
    }
}

fun Events.openBottomSheet(url: String) = screenCoroutine {

    val articleDetail = dataRepository.getArticleDetail(url)

    val data = articleDetail._data
    val isFavorite = articleDetail.isFavorite

    stateManager.updateScreen(ArticlesListState::class) { state ->
        state.copy(bottomSheetStateData =
        BottomSheetStateData(
            open = true,
            isFavorite = isFavorite,
            data = data
        )
        )
    }
}

fun Events.resetBottomSheet() = screenCoroutine {
    stateManager.updateScreen(ArticlesListState::class) { state ->
        state.copy(bottomSheetStateData = state.bottomSheetStateData.copy(open = false))
    }
}
