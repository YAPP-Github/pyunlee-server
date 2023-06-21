package com.yapp.cvs.domain.extension

inline fun <P, R> P?.ifNotNull(callback: (P) -> R): R? {
    if(this != null) return callback(this)
    return null
}