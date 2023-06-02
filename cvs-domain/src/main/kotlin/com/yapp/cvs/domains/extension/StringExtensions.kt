package com.yapp.cvs.domains.extension

fun String.commaStringToLong(): Long {
    return this.replace(",", "").toLong()
}