package com.yapp.cvs.support

import com.yapp.cvs.domains.extension.containsKeywords
import com.yapp.cvs.domains.extension.removeStringsFromStart
import com.yapp.cvs.domains.extension.startsWithKeyword
import com.yapp.cvs.support.ProductDataParser.PBBrandNameRule.NONE

object ProductDataParser {
    fun parseBrandName(title: String, brand: PBBrandNameRule = NONE): String {
        val closeParenthesisIndex = findMismatchedCloseParenthesisIndex(title)
        return when {
            closeParenthesisIndex != -1 -> brand.nameOf(title.substring(0, closeParenthesisIndex))
            title.startsWithKeyword(brand.keywords) -> brand.title
            else -> ""
        }
    }

    fun parseProductName(title: String, brand: PBBrandNameRule = NONE): String {
        val closeParenthesisIndex = findMismatchedCloseParenthesisIndex(title)
        return when {
            closeParenthesisIndex != -1 -> title.substring(closeParenthesisIndex + 1)
            else -> title.removeStringsFromStart(brand.keywords)
        }
    }

    /** 가격 문자열을 숫자로 변환합니다 */
    fun parsePrice(price: String): Long {
        return price.substringBefore('.').replace(
            Regex("\\D+"),
            "",
        ).toLongOrNull() ?: 0
    }

    /** 주어진 URL 에서 13자리 숫자(유통코드)를 정규식으로 찾습니다 */
    fun parseProductCode(imageURL: String, regex: Regex = Regex("\\d{13}")): String? {
        return regex.find(imageURL)?.value
    }

    private fun findMismatchedCloseParenthesisIndex(input: String): Int {
        var openParentheses = 0
        for ((index, char) in input.withIndex()) {
            when (char) {
                '(' -> openParentheses++
                ')' -> {
                    if (openParentheses == 0) {
                        return index
                    }
                    openParentheses--
                }
            }
        }
        return -1
    }

    enum class PBBrandNameRule(val keywords: List<String>, val title: String) {
        CU(listOf("del"), "HEYROO"),
        GS25(listOf("Y(P)", "유어스(P)", "Y"), "유어스"),
        NONE(listOf(), "")
        ;

        fun nameOf(input: String): String {
            return when {
                input.containsKeywords(keywords) -> title
                else -> input
            }
        }
    }
}
