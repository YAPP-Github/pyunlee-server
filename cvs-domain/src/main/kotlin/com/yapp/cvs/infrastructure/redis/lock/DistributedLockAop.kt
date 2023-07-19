package com.yapp.cvs.infrastructure.redis.lock

import com.yapp.cvs.domain.extension.ifNotNull
import com.yapp.cvs.exception.InvalidLockException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import kotlin.reflect.full.declaredMemberProperties

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
        val methodArguments = joinPoint.args

        return keys.joinToString(separator = ":") { key ->
            val indexOfKey = methodParameterNames.indexOf(key)
            val field = methodArguments.getOrNull(indexOfKey) ?: throw InvalidLockException("Invalid Lock Key")
            getPropertyAsString(field)
        }
    }

    fun getPropertyAsString(field: Any): String {
        return if (isPrimitiveType(field)) {
            field.toString()
        } else {
            field::class.declaredMemberProperties
                    .filter { isPrimitiveType(it.getter.call(field)) }
                    .joinToString(":") { it.getter.call(field).toString() }
                    .ifEmpty {  throw InvalidLockException("No valid properties found for the given keys") }
        }
    }

    private fun isPrimitiveType(property: Any?): Boolean {
        return property.ifNotNull { it::class.javaPrimitiveType != null } ?: false
    }
}
