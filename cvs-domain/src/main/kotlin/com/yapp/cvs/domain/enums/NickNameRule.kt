package com.yapp.cvs.domain.enums

import kotlin.random.Random

object NickNameRule {
    private val FIRST_NAME = listOf(
        "기분나쁜", "기분좋은", "신바람나는", "상쾌한", "짜릿한", "그리운", "자유로운", "서운한", "울적한", "비참한", "위축되는", "긴장되는", "두려운", "당당한", "배부른", "수줍은", "창피한", "멋있는",
        "열받은", "심심한", "잘생긴", "이쁜", "시끄러운"
    )

    private val LAST_NAME = listOf(
        "사자", "코끼리", "호랑이", "곰", "여우", "늑대", "너구리", "침팬치", "고릴라", "참새", "고슴도치", "강아지", "고양이", "거북이", "토끼", "앵무새", "하이에나", "돼지", "하마", "원숭이", "물소", "얼룩말", "치타",
        "악어", "기린", "수달", "염소", "다람쥐", "판다"
    )

    fun generateNickName(): String {
        val randomNumber = Random.nextInt(10000, 99999)
        return FIRST_NAME.shuffled()[0] + LAST_NAME.shuffled()[0] + "#$randomNumber"
    }
}
