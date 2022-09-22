package com.muralex.multiplatform.datalayer.sources

import com.muralex.multiplatform.datalayer.objects.ArticleData
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object CacheObjects {
   // internal val countryExtraData: MutableMap<String, CountryExtraData> by lazy { mutableMapOf() }

    var articlesList: List<ArticleData> = emptyList()

}