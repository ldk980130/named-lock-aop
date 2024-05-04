package org.example.namedlockaop.lock

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.lang.reflect.Method

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
        val locking = joinPoint.getAnnotation(Locking::class.java)
        val prefix = locking.keyPrefix

        return joinPoint.getArgValue(LockKey::class.java)
            ?.let { "$prefix:$it" }
            ?: prefix
    }

    private fun <T : Annotation> ProceedingJoinPoint.getAnnotation(annotation: Class<T>): T {
        return method().getAnnotation(annotation)
    }

    private fun <T : Annotation> ProceedingJoinPoint.getArgValue(annotation: Class<T>): String? {
        val parameters = method().parameters
        return parameters
            .find { it.isAnnotationPresent(annotation) }
            ?.let { args[parameters.indexOf(it)] }
            ?.toString()
    }

    private fun ProceedingJoinPoint.method(): Method = (signature as MethodSignature).method
}
