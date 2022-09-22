package com.muralex.multiplatform.datalayer

import com.muralex.multiplatform.datalayer.sources.CacheObjects
import com.muralex.multiplatform.datalayer.sources.localsettings.MySettings
import com.muralex.multiplatform.datalayer.sources.webservices.ApiClient
import com.russhwolf.settings.Settings

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class Repository(val settings: Settings = Settings(), val useDefaultDispatcher: Boolean = true) {

    internal val webservices by lazy { ApiClient() }
    internal val runtimeCache get() = CacheObjects
    internal val localSettings by lazy { MySettings(settings) }

    suspend fun <T> withRepoContext (block: suspend () -> T) : T {
        return if (useDefaultDispatcher) {
            withContext(Dispatchers.Default) {
                block()
            }
        } else {
            block()
        }
    }

}