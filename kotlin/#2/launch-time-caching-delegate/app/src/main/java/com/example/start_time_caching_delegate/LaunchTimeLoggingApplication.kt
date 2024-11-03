package com.example.start_time_caching_delegate

import android.app.Application
import com.example.start_time_caching_delegate.delegates.LaunchTimeCachingDelegate
import com.example.start_time_caching_delegate.unil.startLaunchTimeLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LaunchTimeLoggingApplication: Application() {
    private var launchTime: Long by LaunchTimeCachingDelegate()
    private var launchTimeLoggerJob: Job? = null

    override fun onCreate() {
        super.onCreate()
        launchTime = System.currentTimeMillis()

        launchTimeLoggerJob = CoroutineScope(Dispatchers.Default).launch(Dispatchers.Default) {
            startLaunchTimeLogging(launchTime)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        launchTimeLoggerJob?.cancel()
    }
}