package com.example.leonardo.waiterapp.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.leonardo.waiterapp.data.customers.CustomersDao
import com.example.leonardo.waiterapp.data.reservations.Reservation
import com.example.leonardo.waiterapp.data.reservations.ReservationsDao
import com.example.leonardo.waiterapp.data.tables.TablesDao
import com.example.leonardo.waiterapp.ui.customers.Customer
import com.example.leonardo.waiterapp.ui.tables.Table

@Database(entities = [Customer::class, Table::class, Reservation::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun customerDao(): CustomersDao

    abstract fun tablesDao(): TablesDao

    abstract fun reservationsDao(): ReservationsDao

    companion object {

        private const val APP_DATABASE_NAME = "appDatabase.db"
        private val LOCK = Object()

        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(LOCK) {
                    instance = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, APP_DATABASE_NAME)
                            .build()
                }
            }
            return instance!!
        }
    }
}