package com.google.wear.whereami.data

private val PERSIAN_DIGITS = charArrayOf(
    '0' + 1728,
    '1' + 1728,
    '2' + 1728,
    '3' + 1728,
    '4' + 1728,
    '5' + 1728,
    '6' + 1728,
    '7' + 1728,
    '8' + 1728,
    '9' + 1728
)
val Number.withPersianDigits: String
    get() = "$this".withPersianDigits

val String.withPersianDigits: String
    get() = StringBuilder().also { builder ->
        toCharArray().forEach {
            builder.append(
                when {
                    Character.isDigit(it) -> PERSIAN_DIGITS["$it".toInt()]
                    it == '.' -> "/"
                    else -> it
                }
            )
        }
    }.toString()
class MyDateModel(val dayOfWeek: Int, val monthName: String?, val day: Int) {



    fun weekDayName(): String {
        return when (dayOfWeek) {
            0 -> "شنبه"
            1 -> "یک شنبه"
            2 -> "دو شنبه"
            3 -> "سه شنبه"
            4 -> "چهار شنبه"
            5 -> "پنج شنبه"
            6 -> "جمعه"
            else -> return "";
        }
    }

}