package com.example.leonardo.waiterapp.data

import com.example.leonardo.waiterapp.ui.customers.Customer
import retrofit2.Call
import retrofit2.http.GET

interface Webservice {

    @GET("customer-list.json")
    fun getCustomers(): Call<List<Customer>>

    @GET("table-map.json")
    fun getTables(): Call<List<Boolean>>
}