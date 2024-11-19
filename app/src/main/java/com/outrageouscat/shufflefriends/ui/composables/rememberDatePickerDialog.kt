package com.outrageouscat.shufflefriends.ui.composables

import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

@Composable
fun rememberDatePickerDialog(onDateSelected: (Int, Int) -> Unit): DatePickerDialog {
    val calendar = Calendar.getInstance()
    return DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            onDateSelected(dayOfMonth, month + 1)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )
}
