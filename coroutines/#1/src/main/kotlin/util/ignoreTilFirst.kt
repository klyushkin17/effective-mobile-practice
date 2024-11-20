package org.example.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit

// ignoreTilFirst() - аналог оператора из throttleFirst() из RxJava для Flow

suspend fun <T> Flow<T>.ignoreTilFirst(
    ignoreTime: Long,
    timeUnit: TimeUnit = TimeUnit.MILLISECONDS,
): Flow<T> = flow {
    var lastCollectionTimeMillis = 0L
    val ignoreTimeMillis = when(timeUnit) {
        TimeUnit.NANOSECONDS -> ignoreTime / 1_000_000
        TimeUnit.MICROSECONDS -> ignoreTime / 1_000
        TimeUnit.MILLISECONDS -> ignoreTime
        TimeUnit.SECONDS -> ignoreTime * 1_000
        TimeUnit.MINUTES -> ignoreTime * 60 * 1_000
        TimeUnit.HOURS -> ignoreTime * 60 * 60 * 1_000
        TimeUnit.DAYS -> ignoreTime * 25 * 60 * 60 * 1_000
    }

    collect { value ->
        val currentTimeMillis = System.currentTimeMillis()

        if (currentTimeMillis - lastCollectionTimeMillis > ignoreTimeMillis) {
            emit(value)
            lastCollectionTimeMillis = currentTimeMillis
        }
    }
}