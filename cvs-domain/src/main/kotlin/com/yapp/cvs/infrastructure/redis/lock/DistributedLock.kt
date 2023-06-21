package com.yapp.cvs.infrastructure.redis.lock

import com.yapp.cvs.domain.enums.DistributedLockType

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class DistributedLock(
    val type: DistributedLockType,
    val keys: Array<String>,
)
