package com.gabs.poc.coroutines.service

import com.gabs.poc.coroutines.controller.User
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import java.net.HttpURLConnection
import java.net.URL

@Service
class UserService() {

    private val webClient by lazy { WebClient.create("http://localhost:9000") }

    fun verifyEmail(user: User): String {
        val connection = URL("http://localhost:9000/verifyEmail").openConnection() as HttpURLConnection
        return try {
            connection.requestMethod = "GET"
            connection.inputStream.bufferedReader().use { reader ->
                reader.readText()
            }
        } finally {
            connection.disconnect()
        }
    }

    fun verifyName(user: User): String {
        val connection = URL("http://localhost:9000/verifyEmail").openConnection() as HttpURLConnection
        return try {
            connection.requestMethod = "GET"
            connection.inputStream.bufferedReader().use { reader ->
                reader.readText()
            }
        } finally {
            connection.disconnect()
        }
    }

    suspend fun verifyEmailSuspend(user: User): String =
        webClient.get()
            .uri("/verifyEmail")
            .retrieve()
            .awaitBody<String>()

    suspend fun verifyNameSuspend(user: User): String =
        webClient.get()
            .uri("/verifyEmail")
            .retrieve()
            .awaitBody<String>()

}