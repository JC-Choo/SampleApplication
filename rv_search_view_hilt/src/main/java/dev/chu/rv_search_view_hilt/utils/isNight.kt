package dev.chu.rv_search_view_hilt.utils

import java.util.*

fun isNight(): Boolean {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return (currentHour <= 7 || currentHour >= 18)
}