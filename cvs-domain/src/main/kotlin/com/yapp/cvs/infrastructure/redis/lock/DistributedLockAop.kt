package com.yapp.cvs.infrastructure.redis.lock

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RedissonClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class DistributedLockAop(
    private val redissonClient: RedissonClient,
    private val aopForNewTransaction: AopForNewTransaction
) {
    @Around("@annotation(com.yapp.cvs.infrastructure.redis.lock.DistributedLock)")
    fun lock(joinPoint: ProceedingJoinPoint): Any? {
        val methodSignature = joinPoint.signature as MethodSignature
        val method = methodSignature.method
        val distributedLock: DistributedLock = method.getAnnotation(DistributedLock::class.java)
        val dynamicKey = createDynamicKeyFromMethodArg(
            methodSignature.parameterNames,
            joinPoint.args,
            distributedLock.key
        )
        val rLock = redissonClient.getLock("${distributedLock.lockName}:$dynamicKey")

        try {
            val available = rLock.tryLock(distributedLock.waitTime, distributedLock.leaseTime, distributedLock.timeUnit)
            if (!available) {
                throw Exception("RedissonLock Not Available")
            }
            return aopForNewTransaction.proceed(joinPoint)
        } catch (e: InterruptedException) {
            throw e
        } finally {
            try {
                rLock.unlock()
            } catch (e: IllegalMonitorStateException) {
                log.info("RedissonLock Already unlocked", e)
            }
        }
    }

    fun createDynamicKeyFromMethodArg(
        methodParameterNames: Array<String>,
        methodArgs: Array<Any>,
        key: String
    ): String {
        val keyIndex = methodParameterNames.indexOf(key)
        return methodArgs.getOrNull(keyIndex)?.toString() ?: throw Exception("Bad RedissonLock Key")
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }
}
