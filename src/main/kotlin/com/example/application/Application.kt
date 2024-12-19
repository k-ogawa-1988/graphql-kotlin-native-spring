package com.example.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ImportRuntimeHints

@SpringBootApplication
@ImportRuntimeHints(CustomRuntimeHints::class)
open class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
