package com.example.start_time_caching_delegate.unil

import android.util.Log
import com.example.start_time_caching_delegate.converters.toDisplayableTimeFormat
import kotlinx.coroutines.delay

const val TAG = "LaunchTime"

suspend fun startLaunchTimeLogging(
    launchTime: Long
) {
    while (true) {
        Log.d(
            TAG,
            "The launching time of the session is ${launchTime.toDisplayableTimeFormat()}"
        )
        delay(3000L)
    }
}