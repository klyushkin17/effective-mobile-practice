package com.example.start_time_caching_delegate.delegates

import kotlin.reflect.KProperty

class LaunchTimeCachingDelegate {
    private var cachedLaunchTime: Long = 0L

    operator fun getValue(thisRef: Any?, property: KProperty<*>): Long {
        return cachedLaunchTime
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) {
        cachedLaunchTime = value
    }
}