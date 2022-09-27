package com.muralex.multiplatform.datalayer.sources.webservices

import com.muralex.multiplatform.datalayer.objects.NetworkResult
import com.muralex.multiplatform.datalayer.sources.localsettings.SourceCountry
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

    // 6a509f59b2034acaa5e5318a917fba34
    // b7122b5c5f8948eda9715867b6240ce6
    // //"https://newsapi.org/v2/top-headlines?country=ca&pageSize=20&apiKey=6a509f59b2034acaa5e5318a917fba34"
    // test "https://newsapi.amiron.repl.co/" or https://newsapi.amiron.repl.co/data_ca.json

    companion object {
        private const val testBasUrl = "https://newsapi.amiron.repl.co/"
        const val baseApiUrl = "https://newsapi.org/v2/"
        val endpoint = "top-headlines?"
        val countryParam = "country="
        val pageSizeParam = "pageSize=30"
        val apiKeyParam = "apiKey=6a509f59b2034acaa5e5318a917fba34"

        fun getTestUrl() = testBasUrl
        fun getBaseUrl() = baseApiUrl

        fun getNewsApiEndpointByCountry(country: String) =
            "$endpoint$countryParam$country&$pageSizeParam&$apiKeyParam"

        fun getTestApiEndpointByCountry(country: String) =
            "data_$country.json"

    }

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
            getBaseUrl() + endpoint
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
