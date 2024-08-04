package com.gabs.poc.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.lang.Thread.sleep
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.coroutines.EmptyCoroutineContext

@SpringBootTest
class CoroutinesPocApplicationTests {

	@Test
	fun contextLoads() {
		var start = LocalDateTime.now()
		runBlocking {
			launch {
				delay(500)
				println(Thread.currentThread().name)
			}
			launch {
				delay(500)
				println(Thread.currentThread().name)
			}
		}
		var end = LocalDateTime.now()
		println("TOTAL TIME COROUTINES: ${Duration.between(start, end).toMillis()}")

		start = LocalDateTime.now()
		runBlocking {
			launch {
				sleep(500)
				println(Thread.currentThread().name)
			}
			launch {
				sleep(500)
				println(Thread.currentThread().name)
			}
		}
		end = LocalDateTime.now()
		println("TOTAL TIME THREADS: ${Duration.between(start, end).toMillis()}")

		start = LocalDateTime.now()
		runBlocking(context = Dispatchers.IO) {
			launch {
				sleep(500)
				println(Thread.currentThread().name)
			}
			launch {
				sleep(500)
				println(Thread.currentThread().name)
			}
		}
		end = LocalDateTime.now()
		println("TOTAL TIME THREADS WITH DISPATCHER: ${Duration.between(start, end).toMillis()}")
	}
}
