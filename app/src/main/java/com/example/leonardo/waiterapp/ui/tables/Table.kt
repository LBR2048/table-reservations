package com.example.leonardo.waiterapp.ui.tables

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.leonardo.waiterapp.ui.customers.Customer

@Entity(tableName = "tables",
        indices = [Index("customerId")],
        foreignKeys = [ForeignKey(entity = Customer::class, parentColumns = ["id"],
                childColumns = ["customerId"])])
data class Table(

        @PrimaryKey
        val id: Int,

        var available: Boolean,

        var customerId: Int? = null) {

    override fun toString() = "$id $available"
}