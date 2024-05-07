package org.example.namedlockaop.lock

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Locking(

    val keyPrefix: String = "",

    val timeout: Int = 1
)
