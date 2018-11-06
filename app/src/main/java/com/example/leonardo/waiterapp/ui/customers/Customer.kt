package com.example.leonardo.waiterapp.ui.customers

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "customers")
data class Customer(

        @PrimaryKey
        @SerializedName("id")
        val id: Int,

        @SerializedName("customerFirstName")
        val firstName: String,

        @SerializedName("customerLastName")
        val lastName: String) {

    override fun toString() = "$firstName $lastName".trim()
}