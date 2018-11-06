package com.example.leonardo.waiterapp.data

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class ClearReservationsWorker(context: Context, workerParameters: WorkerParameters)
    : Worker(context, workerParameters) {

    private val appDatabase = AppDatabase.getInstance(context)

    override fun doWork(): Result {
        return try{
            appDatabase.tablesDao().deleteAll()
            Result.SUCCESS
        } catch (throwable: Throwable) {
            Result.FAILURE
        }
    }
}