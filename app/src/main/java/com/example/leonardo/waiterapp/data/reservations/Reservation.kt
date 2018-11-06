package com.example.leonardo.waiterapp.data.reservations

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservations")
data class Reservation(

        @PrimaryKey(autoGenerate = true)
        val id: Int = 0,

        val tableId: String,

        val customerId: String) {

    override fun toString() = "Customer $customerId on table $tableId"
}