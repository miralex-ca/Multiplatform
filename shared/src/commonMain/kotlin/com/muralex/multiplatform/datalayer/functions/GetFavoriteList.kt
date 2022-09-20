package com.muralex.multiplatform.datalayer.functions

import com.muralex.multiplatform.datalayer.Repository
import com.muralex.multiplatform.datalayer.objects.ArticleData
import com.muralex.multiplatform.viewmodel.screens.favoriteslist.FavoriteListItem

suspend fun Repository.getFavoriteListData(): List<FavoriteListItem> = withRepoContext {
   // emptyList()

//    webservices.fetchNewsList()?.apply {
//        println(this)
//    }

    val list = mutableListOf<FavoriteListItem>()

    val fakeItem = FavoriteListItem(
        ArticleData(
            title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            url = "https://helpx.adobe.com/content/dam/help/en/photoshop/using/convert-color-image-black-white/jcr_content/main-pars/before_and_after/image-before/Landscape-Color.jpg",
            description = "",
            image = "",
            text = "",
            publishedAt = "",
            publishedTime = 0L,
            source = ""
        )

    )

    repeat(3) {
        list.add(fakeItem)
    }

    list




}