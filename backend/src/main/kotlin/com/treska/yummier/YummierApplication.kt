package com.treska.yummier

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class YummierApplication

fun main(args: Array<String>) {
    runApplication<YummierApplication>(*args)
}
