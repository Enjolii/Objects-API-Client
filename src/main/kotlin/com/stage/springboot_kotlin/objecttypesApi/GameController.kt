package com.stage.springboot_kotlin.objecttypesApi

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/games/client")
class GameController(private val gameService: GameService) {

    @GetMapping
    fun getObjects(): List<GameData> {
        return gameService.getObjects()
    }

    @PostMapping("/create")
    fun createObjects(@RequestBody requestData: RequestData): GameData {
        return gameService.createObject(requestData)
    }

    @PutMapping("update/{objectUUID}")
    fun updateObjectByUUID(@PathVariable objectUUID: String, @RequestBody requestData: RequestData): GameData {
        return gameService.updateObjectByUUID(objectUUID, requestData)
    }

    @DeleteMapping("delete/{objectUUID}")
    fun deleteObjectByUUID(@PathVariable objectUUID: String): String {
        return gameService.deleteObjectByUUID(objectUUID)
    }

    @GetMapping("/{objectUUID}")
    fun getObjectByUUID(@PathVariable objectUUID: String): GameData {
        return gameService.getObjectByUUID(objectUUID)
    }
}