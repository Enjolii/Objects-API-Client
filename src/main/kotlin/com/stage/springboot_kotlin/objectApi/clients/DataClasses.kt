package com.stage.springboot_kotlin.objectApi.clients

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.JsonNode

data class ObjectsList(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<ObjectWrapper>
)

data class ObjectWrapper(
    val url: String,
    val uuid: String,
    val type: String,
    val record: ObjectRecord
)

data class ObjectRequest(
    val uuid: String?,
    val type: String,
    val record: ObjectRecord
)

data class ObjectRecord(
    val index: Int?,
    val typeVersion: Int,
    val data: JsonNode,
    val geometry: Any?,
    val startAt: String,
    val endAt: String?,
    val registrationAt: String?,
    val correctionFor: String?,
    val correctedBy: String?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class GameData(
    val titel: String,
    val uitgeverij: String,
    val genre: String?,
    val beschrijving: String?,
    val releasedatum: String?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MovieData(
    val titel: String,
    val regisseur: String,
    val duur: Int,
    val cast: List<String>?,
    val genre: String?,
    val beschrijving: String?,
    val releasedatum: String?,
)