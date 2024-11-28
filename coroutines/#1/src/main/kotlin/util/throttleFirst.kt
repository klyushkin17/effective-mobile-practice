package org.example.util

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

fun <T> Flow<T>.throttleFirst(ignoreTime: ULong, timeUnit: TimeUnit): Flow<T> {
    val ignoreTimeMillis = ignoreTime.toMillis(timeUnit)

    var job: Job = Job().apply { complete() }

    return onCompletion { job.cancel() }.run {
        flow {
            coroutineScope {
                collect { value ->
                    if (!job.isActive) {
                        emit(value)
                        job = launch { delay(ignoreTimeMillis.toLong()) }
                    }
                }
            }
        }
    }
}