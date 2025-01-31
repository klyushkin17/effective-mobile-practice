package com.example.androidsdk.workers

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.androidsdk.R
import kotlinx.coroutines.delay
import kotlin.random.Random


class ChargePluggedInWorker(
    private val context: Context,
    private val workerParams: WorkerParameters
): CoroutineWorker(context, workerParams){

    override suspend fun doWork(): Result {
        startForegroundChargePluggedInService()
        delay(1000L)
        return Result.success()
    }

    private suspend fun startForegroundChargePluggedInService() {
        setForeground(
            ForegroundInfo(
                Random.nextInt(),
                NotificationCompat.Builder(context, "charge_plugged_in_channel")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Charger Notification")
                    .setContentText("Charger plugged in!")
                    .build()
            )
        )
    }
}