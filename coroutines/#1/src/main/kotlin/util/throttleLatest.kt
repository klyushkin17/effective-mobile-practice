package org.example.util

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

suspend fun <T> Flow<T>.throttleLatest(
    ignoreTime: ULong,
    timeUnit: TimeUnit = TimeUnit.MILLISECONDS,
): Flow<T> {

    val ignoreTimeMillis = ignoreTime.toMillis(timeUnit)

    return this
        .conflate()
        .transform {
            emit(it)
            delay(ignoreTimeMillis.toLong())
        }
}
