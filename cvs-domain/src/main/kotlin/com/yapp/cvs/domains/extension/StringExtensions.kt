package com.yapp.cvs.domains.extension

fun String.commaStringToLong(): Long {
    return this.replace(",", "").toLong()
}

fun String.containsKeywords(keywords: List<String>): Boolean {
    return keywords.any { this.contains(it, ignoreCase = true) }
}
