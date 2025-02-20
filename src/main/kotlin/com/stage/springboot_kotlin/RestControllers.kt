package com.stage.springboot_kotlin

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/articles/client")
class SecondApiController(private val articleService: ArticleService) {

    @GetMapping("/{slug}")
    fun getArticleViaOtherApi(@PathVariable slug: String): Mono<Article> {
        return articleService.getArticle(slug)
    }

    @GetMapping
    fun getArticlesViaOtherApi(): Mono<List<Article>> {
        return articleService.getArticles()
    }

    @PostMapping
    fun newArticle(@RequestBody article: Article): Mono<Article> {
        return articleService.createArticle(article)
    }

    @DeleteMapping("/{slug}")
    fun deleteArticleViaOtherApi(@PathVariable slug: String): Mono<String> {
        return articleService.deleteArticle(slug)
    }
}