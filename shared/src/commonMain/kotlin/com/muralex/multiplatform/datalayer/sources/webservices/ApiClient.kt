package com.muralex.multiplatform.datalayer.sources.webservices

import com.muralex.multiplatform.datalayer.objects.NetworkResult
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json


class ApiClient {

    val baseUrl = "https://newsapi.org/v2/top-headlines?country=ca&pageSize=20&apiKey=b7122b5c5f8948eda9715867b6240ce6"

    val client = HttpClient {

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }

    }

    suspend inline fun  <reified T : Any> getResponse(
        endpoint: String,
        isPath: Boolean = false
    ) : NetworkResult<T>{

        val url = if (isPath) {
            endpoint
        } else {
            baseUrl + endpoint
        }

        return try {
            val response = client.get(url).body<T>()
            NetworkResult.Success(response)
        } catch (e: Exception) {
            val code = (e as? ResponseException)?.response?.status?.value
            val message = (e as? ResponseException)?.message
            NetworkResult.Failure(code, message)
        }
    }


}
