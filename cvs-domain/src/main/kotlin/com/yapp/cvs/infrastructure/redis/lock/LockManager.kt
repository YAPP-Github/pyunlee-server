package com.yapp.cvs.infrastructure.redis.lock

import org.springframework.stereotype.Component

@Component
interface LockManager {
    fun lock(lockName: String, operation: () -> Any?): Any?
}