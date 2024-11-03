package com.example.start_time_caching_delegate.converters

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
fun Long.toDisplayableTimeFormat(): String {
    val date = Date(this)
    return SimpleDateFormat("hh:mm:ss").format(date)
}