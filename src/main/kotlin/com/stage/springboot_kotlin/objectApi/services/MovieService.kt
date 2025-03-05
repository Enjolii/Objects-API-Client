package com.stage.springboot_kotlin.objectApi.services

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.stage.springboot_kotlin.objectApi.clients.MovieData
import com.stage.springboot_kotlin.objectApi.clients.ObjectRecord
import com.stage.springboot_kotlin.objectApi.clients.ObjectRequest
import com.stage.springboot_kotlin.objectApi.clients.ObjectsAPIClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDate.now
import java.util.*

@Service
class MovieService(
    private val apiClient: ObjectsAPIClient,
    @Value("\${movie.type}") private val movieType: String,
    @Value("\${docker.internal}") private val dockerInternal: String
) {
    val mapper = jacksonObjectMapper()


    fun getAllMovies(): List<MovieData> {
        val clientResponse = apiClient.getObject()

        return clientResponse?.results?.mapNotNull {
            if (it.type.contains(movieType)) mapper.convertValue(it.record.data, MovieData::class.java) else null
        } ?: emptyList()
    }

    fun createMovie(movieData: MovieData): MovieData {
        val mapper = jacksonObjectMapper()
        val objectRequest = ObjectRequest(
            uuid = UUID.randomUUID().toString(),
            type = "$dockerInternal$movieType",
            record = ObjectRecord(
                typeVersion = 1,
                data = mapper.convertValue(movieData, JsonNode::class.java),
                index = null,
                geometry = null,
                startAt = now().toString(),
                endAt = null,
                registrationAt = null,
                correctionFor = null,
                correctedBy = null
            )
        )

        val clientResponse = apiClient.createObject(
            mapper.writeValueAsString(objectRequest)
        )

        clientResponse?.record?.data?.let {
            return mapper.convertValue(it, MovieData::class.java)
        } ?: throw RuntimeException("Failed to create movie")

    }

    fun deleteMovie(movieUUID: String): String? {
        val result = apiClient.deleteObject(movieUUID)
        if (result != null) {
            throw RuntimeException("Failed to delete movie")
        } else {
            return result
        }
    }

    fun updateMovie(movieUUID: String, movieData: MovieData): MovieData {
        val objectRequest = ObjectRequest(
            uuid = movieUUID,
            type = "$dockerInternal$movieType",
            record = ObjectRecord(
                typeVersion = 1,
                data = mapper.convertValue(movieData, JsonNode::class.java),
                index = null,
                geometry = null,
                startAt = now().toString(),
                endAt = null,
                registrationAt = null,
                correctionFor = null,
                correctedBy = null
            )
        )

        val clientResponse = apiClient.updateObject(
            movieUUID,
            mapper.writeValueAsString(objectRequest)
        )

        clientResponse?.record?.data?.let {
            return mapper.convertValue(it, MovieData::class.java)
        } ?: throw RuntimeException("Failed to update movie")
    }
}