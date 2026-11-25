package com.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Headless Spring Boot 4 application: it runs the Kotlin DataFrame sample on startup
 * and exits, there is no web layer involved.
 */
@SpringBootApplication
class DataFrameApplication

fun main(args: Array<String>) {
    runApplication<DataFrameApplication>(*args)
}
