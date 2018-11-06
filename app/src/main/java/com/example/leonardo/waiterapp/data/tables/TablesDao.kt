package com.example.leonardo.waiterapp.data.tables

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.leonardo.waiterapp.ui.tables.Table

@Dao
interface TablesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(tables: List<Table>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(table: Table)

    @Query("SELECT * FROM tables ORDER BY id")
    fun getAll(): LiveData<List<Table>>

    @Query("SELECT * FROM tables ORDER BY id")
    fun getAllSync(): List<Table>

    @Query("SELECT * FROM tables WHERE customerId = :customerId")
    fun getTablesByCustomerSync(customerId: Int): List<Table>

}