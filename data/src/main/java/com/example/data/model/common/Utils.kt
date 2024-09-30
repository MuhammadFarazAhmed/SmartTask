package com.example.data.model.common

import com.example.data.model.model.TaskDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.http.path
import okhttp3.ResponseBody
import java.io.IOException

suspend inline fun <reified T> makeApiCall(
    httpClient: HttpClient,
    method: HttpMethod = HttpMethod.Get,
    block: HttpRequestBuilder.() -> Unit = { url { path("/") } }
): T {
    try {

        val response = when (method) {
            HttpMethod.Get -> httpClient.get { block() }
            HttpMethod.Post -> httpClient.post { block() }
            else -> throw IllegalArgumentException("Unsupported HTTP method: $method")
        }

        return when (response.status.value) {
            in 200..299 -> {
                response.body<T>()
            }

            else -> {
                val errorBody = response.bodyAsText()
                throw IOException("API error: ${response.status.value} - $errorBody")
            }
        }
    } catch (e: Exception) {
        throw e
    }
}

fun unsupported(message: String? = null): Nothing = throw UnsupportedOperationException(message)