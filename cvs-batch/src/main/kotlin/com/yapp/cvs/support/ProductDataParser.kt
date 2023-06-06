package com.yapp.cvs.support

object ProductDataParser {
    /** (유일한)닫는 괄호의 왼쪽 부분을 반환합니다 */
    fun parseBrandName(title: String): String {
        val closeParenthesisIndex = findMismatchedCloseParenthesisIndex(title)
        return when {
            closeParenthesisIndex != -1 -> title.substring(0, closeParenthesisIndex)
            else -> ""
        }
    }

    /** (유일한)닫는 괄호의 오른쪽 부분을 반환합니다 */
    fun parseProductName(title: String): String {
        val closeParenthesisIndex = findMismatchedCloseParenthesisIndex(title)
        return when {
            closeParenthesisIndex != -1 -> title.substring(closeParenthesisIndex + 1)
            else -> title
        }
    }

    /** 가격 문자열을 숫자로 변환합니다 */
    fun parsePrice(price: String): Long {
        return price.replace(
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
}
