package com.stage.springboot_kotlin.objectApi.controllers

import com.stage.springboot_kotlin.objectApi.clients.GameData
import com.stage.springboot_kotlin.objectApi.services.GameService
import com.stage.springboot_kotlin.objectApi.clients.MovieData
import com.stage.springboot_kotlin.objectApi.services.MovieService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/objects")
class ObjectAPIController(
    private val GameService: GameService, private val MovieService: MovieService
) {

    @GetMapping("/all")
    fun getAllObjects(): List<Any> {
        val games = GameService.getAllGames()
        val movies = MovieService.getAllMovies()
        return games + movies
    }

    @GetMapping("/games")
    fun getAllGames() = GameService.getAllGames()

    @GetMapping("/movies")
    fun getAllMovies(): List<MovieData> = MovieService.getAllMovies()

    @PostMapping("/game")
    fun createGame(@RequestBody gameData: GameData): GameData = GameService.createGame(gameData)

    @PostMapping("/movie")
    fun createMovie(@RequestBody movieData: MovieData): MovieData = MovieService.createMovie(movieData)

    @PutMapping("/game/{gameUUID}")
    fun updateGame(@PathVariable gameUUID: String, @RequestBody gameData: GameData): GameData =
        GameService.updateGame(gameUUID, gameData)

    @PutMapping("/movie/{movieUUID}")
    fun updateMovie(@PathVariable movieUUID: String, @RequestBody movieData: MovieData): MovieData =
        MovieService.updateMovie(movieUUID, movieData)

    @DeleteMapping("/game/{gameUUID}")
    fun deleteGame(@PathVariable gameUUID: String): String? = GameService.deleteGame(gameUUID)

    @DeleteMapping("/movie/{movieUUID}")
    fun deleteMovie(@PathVariable movieUUID: String): String? = MovieService.deleteMovie(movieUUID)
}