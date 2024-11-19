package com.outrageouscat.shufflefriends.ui.util

import android.content.Context
import com.outrageouscat.shufflefriends.R

fun Int.toMonthName(context: Context): String {
    val months = context.resources.getStringArray(R.array.months)
    return if (this in 1..12) months[this - 1] else ""
}
