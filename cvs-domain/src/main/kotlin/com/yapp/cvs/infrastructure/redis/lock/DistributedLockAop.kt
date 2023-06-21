package com.yapp.cvs.infrastructure.redis.lock

import com.yapp.cvs.exception.InvalidLockException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.stereotype.Component

@Aspect
@Component
class DistributedLockAop(
        private val lockManager: LockManager,
) {
    @Around("@annotation(distributedLock)")
    fun lock(joinPoint: ProceedingJoinPoint, distributedLock: DistributedLock): Any? {
        val dynamicKey = createDynamicKey(joinPoint, distributedLock.keys)
        val lockName = "${distributedLock.type.lockName}:$dynamicKey"
        return lockManager.lock({ joinPoint.proceed() }, lockName)
    }

    private fun createDynamicKey(joinPoint: ProceedingJoinPoint, keys: Array<String>): String {
        val methodSignature = joinPoint.signature as MethodSignature
        val methodParameterNames = methodSignature.parameterNames
        val methodArgs = joinPoint.args

        val dynamicKey = keys.joinToString(separator = ":") { key ->
            val indexOfKey = methodParameterNames.indexOf(key)
            methodArgs.getOrNull(indexOfKey)?.toString() ?: throw InvalidLockException("Invalid Lock Key")
        }
        return dynamicKey
    }
}
