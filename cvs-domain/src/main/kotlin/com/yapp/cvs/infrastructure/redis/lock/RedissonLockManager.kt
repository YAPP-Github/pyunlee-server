package com.yapp.cvs.infrastructure.redis.lock

import com.yapp.cvs.exception.InvalidLockException
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedissonLockManager(
    private val redissonClient: RedissonClient,
    private val transactionManagerSupport: TransactionManagerSupport
): LockManager {
    override fun lock(operation: () -> Any?, lockName: String): Any? {
        val rLock = redissonClient.getLock(lockName)

        try {
            val available = rLock.tryLock(waitTime, leaseTime, timeUnit)
            if (!available) {
                throw InvalidLockException("Redisson Lock Not Available")
            }
            return transactionManagerSupport.execute { operation() }
        } finally {
            rLock.unlock()
        }
    }

    companion object {
        private const val waitTime = 5L
        private const val leaseTime = 3L
        private val timeUnit = TimeUnit.SECONDS
    }
}