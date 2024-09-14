package com.gabs.poc.coroutines.controller

import com.gabs.poc.coroutines.service.UserService
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.lang.Thread.sleep
import java.time.Duration
import kotlin.concurrent.thread

data class User(val id: Int, val name: String)

@RestController
class UserController {

    val userService = UserService()

    @GetMapping("/users")
    @ResponseBody
    fun createUser(): Any {
        Thread.sleep(100)
        return 0
    }

    @GetMapping("/v2/users")
    fun createUserV2(): Mono<User> {
        val user = User(id = 1, name = "John Doe")
        return Mono.just(user).delayElement(Duration.ofMillis(100))
    }

    @GetMapping("/v3/users")
    fun createUserV3(): User {
        val user = User(id = 1, name = "John Doe")
        userService.verifyEmail(user)
        userService.verifyName(user)
        return user
    }

    @GetMapping("/v3/dispatchers/users")
    fun createUserDispatchersV3(): User {
        return runBlocking(context = Dispatchers.IO) {
            val user = User(id = 1, name = "John Doe")
            async { userService.verifyEmailSuspend(user) }.await()
            async { userService.verifyNameSuspend(user) }.await()
            user
        }
    }

    @GetMapping("/v3/coroutines/users")
    suspend fun createUserCoroutineV3(): User = coroutineScope {
            val user = User(id = 1, name = "John Doe")
            async { userService.verifyEmailSuspend(user) }.await()
            async { userService.verifyNameSuspend(user) }.await()
            user
    }

    @GetMapping("/thread")
    fun startTest() {
        val threads = (1..100_000).map {
            thread {
                sleep(5000)
            }
        }
        println("Threads: Ready to Roll")
        threads.forEach{it.join()}
    }

    @GetMapping("/coroutine")
    fun startCoroutines(): Any {
        runBlocking {
            (1..100_000).map {
                launch {
                    delay(5000)
                }
            }
        }
        println("Coroutines: Ready to Roll")
        return 0
    }

}