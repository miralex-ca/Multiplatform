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

    // TODO check url
    // 6a509f59b2034acaa5e5318a917fba34
    //b7122b5c5f8948eda9715867b6240ce6
    val baseUrl =
        //"https://newsapi.org/v2/top-headlines?country=ca&pageSize=20&apiKey=6a509f59b2034acaa5e5318a917fba34"
        "https://newsapi.amiron.repl.co/data.json"

    val client = HttpClient {

        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(Logging) {
            logger = Logger.SIMPLE
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

            println("fail not")

            NetworkResult.Success(response)
        } catch (e: Exception) {
            println("fail")
            val code = (e as? ResponseException)?.response?.status?.value
            val message = (e as? ResponseException)?.message
            NetworkResult.Failure(code, message)
        }
    }


}
