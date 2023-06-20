package com.yapp.cvs.domain.extension

inline fun <P, R> Collection<P>?.ifNotEmpty(callback: (Collection<P>) -> R): R? {
    if (!this.isNullOrEmpty()) {
        return callback(this)
    }
    return null
}