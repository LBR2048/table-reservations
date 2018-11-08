package com.example.leonardo.waiterapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.leonardo.waiterapp.data.customers.CustomersDao
import com.example.leonardo.waiterapp.data.tables.TablesDao
import com.example.leonardo.waiterapp.ui.customers.Customer
import com.example.leonardo.waiterapp.ui.tables.Table

// TODO In a future version add a reservations table associating keys from
// tables and customers
@Database(entities = [Customer::class, Table::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun customerDao(): CustomersDao

    abstract fun tablesDao(): TablesDao

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