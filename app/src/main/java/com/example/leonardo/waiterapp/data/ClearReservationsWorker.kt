package com.example.leonardo.waiterapp.data

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class ClearReservationsWorker(context: Context, workerParameters: WorkerParameters)
    : Worker(context, workerParameters) {

    private val appDatabase = AppDatabase.getInstance(context)

    override fun doWork(): Result {
        return try{
            clearAllReservations()
            Result.SUCCESS
        } catch (throwable: Throwable) {
            Result.FAILURE
        }
    }

    // TODO add this method to a repository and test it
    private fun clearAllReservations() {
        val tables = appDatabase.tablesDao().getAllSync()
        tables.map {
            it.available = true
            it.customerId = null
            appDatabase.tablesDao().insert(it)
        }
    }
}