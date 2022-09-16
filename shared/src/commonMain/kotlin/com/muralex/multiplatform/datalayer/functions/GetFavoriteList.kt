package com.muralex.multiplatform.datalayer.functions

import com.muralex.multiplatform.datalayer.Repository
import com.muralex.multiplatform.datalayer.objects.ArticlesListData
import com.muralex.multiplatform.viewmodel.screens.articleslist.ArticlesListItem
import com.muralex.multiplatform.datalayer.sources.webservices.apis.fetchNewsList
import com.muralex.multiplatform.viewmodel.screens.articleslist.FavoriteListItem

suspend fun Repository.getFavoriteListData(): List<FavoriteListItem> = withRepoContext {
   // emptyList()

//    webservices.fetchNewsList()?.apply {
//        println(this)
//    }

    val list = mutableListOf<FavoriteListItem>()

    val fakeItem = FavoriteListItem(
        ArticlesListData(
            title = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt",

            imageUrl = "https://helpx.adobe.com/content/dam/help/en/photoshop/using/convert-color-image-black-white/jcr_content/main-pars/before_and_after/image-before/Landscape-Color.jpg")
    )

    repeat(3) {
        list.add(fakeItem)
    }

    list




}