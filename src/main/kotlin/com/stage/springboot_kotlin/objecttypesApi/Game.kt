package com.stage.springboot_kotlin.objecttypesApi

data class ApiResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Results>
)

data class Results(
    val url: String,
    val uuid: String,
    val type: String,
    val record: Record
)

data class RequestData(
    val type: String,
    val record: Record
)

data class Record(
    val index: Int?,
    val typeVersion: Int,
    val data: GameData,
    val geometry: Any?,
    val startAt: String,
    val endAt: String?,
    val registrationAt: String?,
    val correctionFor: Any?,
    val correctedBy: Any?
)

data class GameData(
    val titel: String,
    val uitgeverij: String,
    val genre: String?,
    val beschrijving: String?,
    val releasedatum: String?
)