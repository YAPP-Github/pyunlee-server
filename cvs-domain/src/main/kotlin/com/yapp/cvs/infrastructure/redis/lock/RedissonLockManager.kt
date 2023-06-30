package com.yapp.cvs.infrastructure.redis.lock

import com.yapp.cvs.exception.InvalidLockException
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit

@Component
class RedissonLockManager(
    private val redissonClient: RedissonClient,
): LockManager {
    // 트랜잭션이 모두 Lock 내부에서 실행됨을 보장한다
    @Transactional(propagation = Propagation.NEVER)
    override fun lock(lockName: String, operation: () -> Any?): Any? {
        val rLock = redissonClient.getLock(lockName)

        try {
            val available = rLock.tryLock(waitTime, leaseTime, timeUnit)
            if (!available) {
                throw InvalidLockException("Redisson Lock Not Available")
            }
            return operation()
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