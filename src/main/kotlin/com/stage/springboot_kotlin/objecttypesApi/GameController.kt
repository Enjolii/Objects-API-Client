package com.stage.springboot_kotlin.objecttypesApi

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/game/client")
class GameController(private val gameService: ObjectsAPIClientOLD) {

    @GetMapping
    fun getObjects(): List<Any> {
        return gameService.getObjects()
    }

    @PostMapping("/create")
    fun createObjects(@RequestBody requestData: RequestData): Any {
        return gameService.createObject(requestData)
    }

    @PutMapping("update/{objectUUID}")
    fun updateObjectByUUID(@PathVariable objectUUID: String, @RequestBody requestData: RequestData): Any {
        return gameService.updateObjectByUUID(objectUUID, requestData)
    }

    @DeleteMapping("delete/{objectUUID}")
    fun deleteObjectByUUID(@PathVariable objectUUID: String): String {
        return gameService.deleteObjectByUUID(objectUUID)
    }

    @GetMapping("/{objectUUID}")
    fun getObjectByUUID(@PathVariable objectUUID: String): Any {
        return gameService.getObjectByUUID(objectUUID)
    }
}