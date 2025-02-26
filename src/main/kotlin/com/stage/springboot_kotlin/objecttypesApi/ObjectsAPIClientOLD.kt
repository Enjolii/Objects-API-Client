package com.stage.springboot_kotlin.objecttypesApi

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient


@Service
class ObjectsAPIClientOLD(
    @Value("\${api.base.url.object}") private val apiBaseUrl: String,
    @Value("\${api.token.object}") private val token: String,
    @Value("\${game.type}") private val gameType: String,
    @Value("\${movie.type}") private val movieType: String
) {

    private val restClient: RestClient = RestClient.create(apiBaseUrl)
    private val mapper = jacksonObjectMapper()

    fun getGames(): List<Any> {
        val response = restClient.get()
            .uri("/objects")
            .header("Authorization", "Token $token")
            .retrieve()
            .body(ApiResponse::class.java)

        return response?.results?.map { it.record.data } ?: emptyList()
    }

    fun getObjects(): List<Any> {
        val response = restClient.get()
            .uri("/objects")
            .header("Authorization", "Token $token")
            .retrieve()
            .body(ApiResponse::class.java)

        return response?.results?.mapNotNull {
            when {
                it.type.contains(gameType) -> mapper.convertValue(it.record.data, GameData::class.java)
                it.type.contains(movieType) -> mapper.convertValue(it.record.data, MovieData::class.java)
                else -> null
            }
        } ?: emptyList()
    }

    fun createObject(requestData: RequestData): Any {
        val requestBody = mapper.writeValueAsString(requestData)
        val response = restClient.post()
            .uri("/objects")
            .header("Authorization", "Token $token")
            .header("Content-Crs", "EPSG:4326")
            .header("Content-Type", "application/json")
            .body(requestBody)
            .retrieve()
            .body(Results::class.java)

        return response?.record?.data ?: throw RuntimeException("Failed to create object")
    }

    fun updateObjectByUUID(objectUUID: String, requestData: RequestData): Any {
        val requestBody = mapper.writeValueAsString(requestData)
        val response = restClient.put()
            .uri("/objects/$objectUUID")
            .header("Authorization", "Token $token")
            .header("Content-Crs", "EPSG:4326")
            .header("Content-Type", "application/json")
            .body(requestBody)
            .retrieve()
            .body(Results::class.java)

        return response?.record?.data ?: throw RuntimeException("Failed to update object")
    }

    fun deleteObjectByUUID(objectUUID: String): String {
        val response = restClient.delete()
            .uri("/objects/$objectUUID")
            .header("Authorization", "Token $token")
            .retrieve()
            .body(String::class.java)

        return if (response != null) {
            "object with ID $objectUUID not found or could not be deleted $response"
        } else {
            "object with ID $objectUUID deleted successfully"
        }
    }

    fun getObjectByUUID(objectUUID: String): Any {
        val response = restClient.get()
            .uri("/objects/$objectUUID")
            .header("Authorization", "Token $token")
            .retrieve()
            .body(Results::class.java)

        return response?.record?.data ?: throw RuntimeException("Failed to find object")
    }
}