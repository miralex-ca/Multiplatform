package com.muralex.multiplatform.datalayer

import com.muralex.multiplatform.NewsDb
import com.muralex.multiplatform.datalayer.sources.CacheObjects
import com.muralex.multiplatform.datalayer.sources.localsettings.MySettings
import com.muralex.multiplatform.datalayer.sources.webservices.ApiClient
import com.russhwolf.settings.Settings
import com.squareup.sqldelight.db.SqlDriver

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class Repository(
    val sqlDriver: SqlDriver,
    val settings: Settings = Settings(),
    val useDefaultDispatcher: Boolean = true,
) {

    internal val webservices by lazy { ApiClient() }
    internal val runtimeCache get() = CacheObjects
    internal val localSettings by lazy { MySettings(settings) }
    internal val localDb by lazy { NewsDb(sqlDriver) }

    suspend fun <T> withRepoContext(block: suspend () -> T): T {
        return if (useDefaultDispatcher) {
            withContext(Dispatchers.Default) {
                block()
            }
        } else {
            block()
        }
    }

}