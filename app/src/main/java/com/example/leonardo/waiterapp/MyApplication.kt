package com.example.leonardo.waiterapp

import android.app.Application
import android.preference.PreferenceManager
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.leonardo.waiterapp.data.ClearReservationsWorker

import com.facebook.stetho.Stetho
import java.util.concurrent.TimeUnit

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val appFirstStart = preferences.getBoolean(PREFS_APP_FIRST_START, true)
        if (appFirstStart) {
            val clearReservationsWorkRequest = PeriodicWorkRequest.Builder(
                    ClearReservationsWorker::class.java, 15, TimeUnit.MINUTES).build()
            WorkManager.getInstance().enqueue(clearReservationsWorkRequest)
            preferences.edit().putBoolean(PREFS_APP_FIRST_START, false).apply()
        }
    }

    companion object {

        const val PREFS_APP_FIRST_START = "prefs_app_first_start"
    }
}