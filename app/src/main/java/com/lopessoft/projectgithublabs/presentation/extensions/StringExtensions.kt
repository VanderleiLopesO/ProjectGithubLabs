package com.lopessoft.projectgithublabs.presentation.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String.convertToBRTFormatDate(pattern: String = "yyyy-MM-dd"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    val date = dateFormat.parse(this)
    return dateFormat.format(date)
}