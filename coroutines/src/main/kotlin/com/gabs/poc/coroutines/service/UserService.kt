package com.gabs.poc.coroutines.service

import com.gabs.poc.coroutines.controller.User
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import reactor.core.publisher.Mono
import java.net.HttpURLConnection
import java.net.URL

@Service
class UserService() {

    private val webClient by lazy { WebClient.create("http://localhost:9000") }

    fun verifyEmail(user: User): Boolean {
        val connection = URL("http://localhost:9000/verifyEmail").openConnection() as HttpURLConnection
        return try {
            connection.requestMethod = "GET"
            connection.inputStream.bufferedReader().use { reader ->
                val response = reader.readText()
                response.toBoolean()
            }
        } finally {
            connection.disconnect()
        }
    }

    suspend fun verifyEmailSuspend(user: User): Boolean {
        return webClient.get()
            .uri("/verifyEmail")
            .retrieve()
            .awaitBody<String>()
            .toBoolean()
    }

}