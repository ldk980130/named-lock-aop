package org.example.namedlockaop

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NamedLockAopApplication

fun main(args: Array<String>) {
    runApplication<NamedLockAopApplication>(*args)
}
