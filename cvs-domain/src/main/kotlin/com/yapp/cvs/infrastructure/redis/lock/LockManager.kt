package com.yapp.cvs.infrastructure.redis.lock

import org.springframework.stereotype.Component

@Component
interface LockManager {
    fun lock(operation: () -> Any?, lockName: String): Any?
}