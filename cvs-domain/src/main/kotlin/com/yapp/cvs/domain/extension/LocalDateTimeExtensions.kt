package com.yapp.cvs.domain.extension

import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters.lastDayOfMonth


fun LocalDateTime.endOfMonth(): LocalDateTime {
    val end = this.with(lastDayOfMonth())
    return LocalDateTime.of(end.year, end.month, end.dayOfMonth, 23, 59, 59, 9999)
}
