package com.yapp.cvs.domain.extension

fun <R> Boolean.ifTrue(callback: (Boolean) -> R): R? {
    if(this) return callback(this)
    return null
}