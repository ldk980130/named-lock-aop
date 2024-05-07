package org.example.namedlockaop.lock

interface LockTemplate {

    fun execute(lockKey: String, timeout: Int, action: () -> Any?): Any?
}
