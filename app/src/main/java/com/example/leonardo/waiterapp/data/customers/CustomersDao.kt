package com.example.leonardo.waiterapp.data.customers

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.leonardo.waiterapp.ui.customers.Customer

@Dao
interface CustomersDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(customers: List<Customer>)

    @Query("SELECT * FROM customers ORDER BY firstName")
    fun getAll(): LiveData<List<Customer>>

    @Query("SELECT * FROM customers ORDER BY firstName")
    fun getAll2(): List<Customer>
}