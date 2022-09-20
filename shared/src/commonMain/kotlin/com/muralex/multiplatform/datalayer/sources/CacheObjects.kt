package com.muralex.multiplatform.datalayer.sources

import com.muralex.multiplatform.datalayer.objects.ArticleData
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object CacheObjects {
    // here is the repository data we decide to just cache temporarily (for the runtime session),
    // rather than caching it permanently in the local db or local settings


   // internal val countryExtraData: MutableMap<String, CountryExtraData> by lazy { mutableMapOf() }

    var articlesList: List<ArticleData> = emptyList()

}