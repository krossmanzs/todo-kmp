package com.krossmanzs.todolearn.network

import com.krossmanzs.todolearn.entity.LaunchListResponse
import com.krossmanzs.todolearn.entity.RocketLaunch
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


// This class executes network requests and deserializes JSON responses into entities from the
// com.krossmanzs.todolearn.entity package. The Ktor HttpClient instance initializes and stores
// the httpClient property.
//
// This code uses the ContentNegotiation Ktor plugin to deserialize the result of a GET request.
// The plugin processes the request and the response payload as JSON, serializing and deserializing
// them as needed.
class SpaceApi {
    private val httpClient = HttpClient {
        // The ContentNegotiation plugin serves two primary purposes:
        //
        // Negotiating media types between the client and server. For this, it uses the Accept and
        // Content-Type headers.
        //
        // Serializing/deserializing the content in a specific format when sending requests and
        // receiving responses. Ktor supports the following formats out-of-the-box: JSON, XML, CBOR,
        // and ProtoBuf.
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    suspend fun getAllLaunches(): List<RocketLaunch> {
        return (
            httpClient.get(
                "https://lldev.thespacedevs.com/2.3.0/launches/previous/?mode=list&format=json"
        ).body() as LaunchListResponse).results
    }
}