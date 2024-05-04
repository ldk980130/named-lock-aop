package org.example.namedlockaop.lock

import org.springframework.stereotype.Component

@Component
class NamedLockManager: LockManager {

    override fun lock(lockKey: String, timeoutMills: Long) {
        TODO("Not yet implemented")
    }

    override fun unlock(lockKey: String) {
        TODO("Not yet implemented")
    }
}
