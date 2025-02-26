package com.stage.springboot_kotlin.objectApi

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDate.now
import java.util.*

@Service
class GameService(
    private val apiClient: ObjectsAPIClient,
    @Value("\${game.type}") private val gameType: String,
    @Value("\${docker.internal}") private val dockerInternal: String
) {
    val mapper = jacksonObjectMapper()

    fun getAllGames(): List<GameData> {
        val clientResponse = apiClient.getObject()

        return clientResponse?.results?.mapNotNull {
            if (it.type.contains(gameType)) mapper.convertValue(it.record.data, GameData::class.java) else null
        } ?: emptyList()
    }

    fun createGame(gameData: GameData): GameData {
        val objectRequest = ObjectRequest(
            uuid = UUID.randomUUID().toString(),
            type = "$dockerInternal$gameType",
            record = ObjectRecord(
                typeVersion = 2,
                data = mapper.convertValue(gameData, JsonNode::class.java),
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
            return mapper.convertValue(it, GameData::class.java)
        } ?: throw RuntimeException("Failed to create game")
    }

    fun deleteGame(gameUUID: String): String? {
        val result = apiClient.deleteObject(gameUUID)
        if (result != null) {
            throw RuntimeException("Failed to delete game")
        } else {
            return result
        }
    }

    fun updateGame(gameUUID: String, gameData: GameData): GameData {
        val objectRequest = ObjectRequest(
            uuid = gameUUID,
            type = "$dockerInternal$gameType",
            record = ObjectRecord(
                typeVersion = 2,
                data = mapper.convertValue(gameData, JsonNode::class.java),
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
            gameUUID,
            (mapper.writeValueAsString(objectRequest))
        )

        clientResponse?.record?.data?.let {
            return mapper.convertValue(it, GameData::class.java)
        } ?: throw RuntimeException("Failed to update game")
    }
}