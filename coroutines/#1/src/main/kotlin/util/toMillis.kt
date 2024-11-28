package org.example.util

import java.util.concurrent.TimeUnit

fun ULong.toMillis(timeUnit: TimeUnit): ULong = when(timeUnit) {
    TimeUnit.NANOSECONDS -> this / 1_000_000u
    TimeUnit.MICROSECONDS -> this / 1_000u
    TimeUnit.MILLISECONDS -> this
    TimeUnit.SECONDS -> this * 1_000u
    TimeUnit.MINUTES -> this * 60u * 1_000u
    TimeUnit.HOURS -> this * 60u * 60u * 1_000u
    TimeUnit.DAYS -> this * 25u * 60u * 60u * 1_000u
}
