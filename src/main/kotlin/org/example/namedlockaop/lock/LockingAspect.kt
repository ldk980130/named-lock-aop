package org.example.namedlockaop.lock

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Aspect
@Order(Integer.MAX_VALUE - 1)
class LockingAspect(
    private val lockManager: LockManager
) {

    @Around("@annotation(org.example.namedlockaop.lock.Locking)")
    fun execute(joinPoint: ProceedingJoinPoint): Any? {
        val lockKey = getLockKey(joinPoint)

        try {
            lockManager.lock(lockKey)
            return joinPoint.proceed()
        } finally {
            lockManager.unlock(lockKey)
        }
    }

    private fun getLockKey(joinPoint: ProceedingJoinPoint): String {
        val method = (joinPoint.signature as MethodSignature).method

        val locking = method.getAnnotation(Locking::class.java)
        val prefix = locking.keyPrefix

        val parameters = method.parameters

        val keyParameter = parameters
            .find { it.isAnnotationPresent(LockKey::class.java) }

        if (keyParameter == null) {
            return prefix
        }

        val index = parameters.indexOf(keyParameter)
        val key = joinPoint.args[index]

        return "$prefix:$key"
    }
}
