package com.yapp.cvs.domain.extension

fun String.commaStringToLong(): Long {
    return this.replace(",", "").toLong()
}

fun String.containsKeywords(keywords: List<String>): Boolean {
    return keywords.any { this.contains(it, ignoreCase = true) }
}

fun String.startsWithKeyword(keywords: List<String>): Boolean {
    return keywords.any { this.startsWith(it, ignoreCase = true) }
}

fun String.removeStringsFromStart(strings: List<String>): String {
    val prefixToRemove = strings.find { this.startsWith(it) }
    return prefixToRemove?.let { this.removePrefix(it) } ?: this
}
