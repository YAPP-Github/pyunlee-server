package com.yapp.cvs.infrastructure.redis.lock

import com.yapp.cvs.exception.InvalidLockException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
class DistributedLockAop(
        private val lockManager: LockManager,
) {
    @Around("@annotation(distributedLock)")
    fun lock(joinPoint: ProceedingJoinPoint, distributedLock: DistributedLock): Any? {
        val dynamicKey = createDynamicKey(joinPoint, distributedLock.keys)
        val lockName = "${distributedLock.type.lockName}:$dynamicKey"
        return lockManager.lock(lockName) { joinPoint.proceed() }
    }

    private fun createDynamicKey(joinPoint: ProceedingJoinPoint, keys: Array<String>): String {
        val methodSignature = joinPoint.signature as MethodSignature
        val methodParameterNames = methodSignature.parameterNames
        val methodArgs = joinPoint.args

        // TODO: args 객체에서 꺼내서 만들수 있게, 지금은 toString임
        val dynamicKey = keys.joinToString(separator = ":") { key ->
            val indexOfKey = methodParameterNames.indexOf(key)
            methodArgs.getOrNull(indexOfKey)?.toString() ?: throw InvalidLockException("Invalid Lock Key")
        }
        return dynamicKey
    }
}
