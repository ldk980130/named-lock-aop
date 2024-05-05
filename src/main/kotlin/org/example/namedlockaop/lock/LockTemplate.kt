package org.example.namedlockaop.lock

interface LockTemplate {

    fun execute(lockKey: String, timeoutMills: Int = 1, action: () -> Any?): Any?
}
