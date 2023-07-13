package com.yapp.cvs.domain.enums

enum class DistributedLockType(
    val lockName: String
) {
    MEMBER("memberLock"),
    PRODUCT_LIKE("productLikeLock"),
    MEMBER_PRODUCT("memberProductLock")
}