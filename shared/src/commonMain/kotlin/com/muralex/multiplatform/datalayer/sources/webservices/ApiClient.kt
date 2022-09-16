package com.muralex.multiplatform.datalayer.sources.webservices

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.*
import io.ktor.client.request.get
import kotlinx.serialization.json.Json

class ApiClient {

    val baseUrl = "https://newsapi.org/v2/top-headlines?country=ca&pageSize=20&apiKey=b7122b5c5f8948eda9715867b6240ce6"

    val client = HttpClient {

        install(JsonFeature) {
            serializer = KotlinxSerializer(Json {
                useAlternativeNames = false
                ignoreUnknownKeys = true
            })
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }

    }


    suspend inline fun <reified T:Any> getResponse(endpoint : String): T? {
        val url = baseUrl+endpoint
        try {
            // please notice, Ktor Client is switching to a background thread under the hood
            // so the http call doesn't happen on the main thread, even if the coroutine has been launched on Dispatchers.Main
            val resp = client.get<T>(url)
            return resp

        } catch (e: Exception) {

        }
        return null
    }


}
