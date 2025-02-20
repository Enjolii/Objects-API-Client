package com.stage.springboot_kotlin

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Service
class ArticleService {

    private val webClient: WebClient = WebClient.builder().baseUrl("http://localhost:8080").build()

    fun getArticle(slug: String): Mono<Article> {
        return webClient.get()
            .uri("/api/articles/$slug")
            .retrieve()
            .onStatus({ status -> status == HttpStatus.NOT_FOUND }) {
                Mono.error(RuntimeException("Article not found"))
            }
            .bodyToMono()
    }

    fun getArticles(): Mono<List<Article>> {
        return webClient.get()
            .uri("/api/articles")
            .retrieve()
            .bodyToMono()
    }

    fun createArticle(article: Article): Mono<Article> {
        return webClient.post()
            .uri("/api/articles")
            .bodyValue(article)
            .retrieve()
            .bodyToMono()
    }

    fun deleteArticle(slug: String): Mono<String> {
        return webClient.delete()
            .uri("/api/articles/$slug")
            .retrieve()
            .onStatus({ status -> status == HttpStatus.NOT_FOUND }) {
                Mono.error(RuntimeException("Failed to delete article"))
            }
            .bodyToMono()
    }
}