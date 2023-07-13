package com.yapp.cvs.domain.enums

enum class ProductLikeType(val displayName: String) {
    LIKE("like"),
    DISLIKE("dislike"),
    NONE("none")
    ;
    fun isLike(): Boolean {
        return this == LIKE
    }

    fun isDislike(): Boolean {
        return this == DISLIKE
    }

}