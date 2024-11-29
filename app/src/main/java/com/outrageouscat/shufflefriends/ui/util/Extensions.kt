package com.outrageouscat.shufflefriends.ui.util

import android.content.Context
import com.outrageouscat.shufflefriends.R

fun Int.toMonthName(context: Context): String {
    val months = context.resources.getStringArray(R.array.months)
    return if (this in 1..12) months[this - 1] else ""
}

fun Int.toOrdinal(): String {
    return when {
        this in 11..13 -> "${this}th" // Special cases for 11, 12 y 13
        this % 10 == 1 -> "${this}st"
        this % 10 == 2 -> "${this}nd"
        this % 10 == 3 -> "${this}rd"
        else -> "${this}th"
    }
}

fun Int.toLocalizedOrdinal(context: Context): String {
    return if (isAppLanguageEnglish(context)) {
        this.toOrdinal()
    } else {
        this.toString()
    }
}

fun isAppLanguageEnglish(context: Context): Boolean {
    val locale = context.resources.configuration.locales[0] // Gets current language
    return locale.language == "en" // verify if it's english
}