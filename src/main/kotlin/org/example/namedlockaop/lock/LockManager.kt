package org.example.namedlockaop.lock

interface LockManager {

    fun lock(lockKey: String, timeoutMills: Long = 1000)

    fun unlock(lockKey: String)
}
