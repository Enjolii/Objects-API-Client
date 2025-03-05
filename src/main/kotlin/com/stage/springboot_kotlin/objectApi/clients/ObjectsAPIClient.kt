package com.stage.springboot_kotlin.objectApi.clients

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class ObjectsAPIClient(
    @Value("\${api.base.url.object}") private val apiBaseUrl: String,
    @Value("\${api.token.object}") private val token: String,
) {

    val restClient = RestClient.create(apiBaseUrl)

    fun getObject(): ObjectsList? {

        val ApiResponse = restClient.get()
            .uri("/objects")
            .header("Authorization", "Token $token")
            .retrieve()
            .body(ObjectsList::class.java)

        return ApiResponse
    }

    fun createObject(objectRequest: String): ObjectWrapper? {

        val result = restClient.post()
            .uri("/objects")
            .header("Authorization", "Token $token")
            .header("Content-Crs", "EPSG:4326")
            .header("Content-Type", "application/json")
            .body(objectRequest)
            .retrieve()
            .body(ObjectWrapper::class.java)

        return result
    }

    fun deleteObject(objectUUID: String): String? {

        val result = restClient.delete()
            .uri("/objects/$objectUUID")
            .header("Authorization", "Token $token")
            .retrieve()
            .body(String::class.java)

        return result
    }

    fun updateObject(objectUUID: String, objectRequest: String): ObjectWrapper? {

        val result = restClient.put()
            .uri("/objects/$objectUUID")
            .header("Authorization", "Token $token")
            .header("Content-Crs", "EPSG:4326")
            .header("Content-Type", "application/json")
            .body(objectRequest)
            .retrieve()
            .body(ObjectWrapper::class.java)

        return result
    }
}