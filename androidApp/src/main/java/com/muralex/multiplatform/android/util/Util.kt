package com.muralex.multiplatform.android.util

import android.util.Log
import java.text.DateFormat
import java.util.*


fun Long.formatToDate(locale: Locale = Locale.US): String {
    var time = ""
    try {
        val df: DateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale)
        time = df.format(this)
    } catch (e: Exception) {
        Log.d("MyTag", "Error: ${e.message}")
    }
    return time
}