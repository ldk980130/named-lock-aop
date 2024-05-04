package org.example.namedlockaop.lock

interface LockTemplate {

    fun execute(lockKey: String, timeoutMills: Int = 1000, action: () -> Any?): Any?
}
