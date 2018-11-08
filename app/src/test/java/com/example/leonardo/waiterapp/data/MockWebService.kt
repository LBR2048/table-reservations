package com.example.leonardo.waiterapp.data

import com.example.leonardo.waiterapp.getDummyLiveBooleans
import com.example.leonardo.waiterapp.getDummyLiveTables
import com.example.leonardo.waiterapp.ui.customers.Customer
import retrofit2.Call
import retrofit2.mock.BehaviorDelegate

class MockWebService(
        private val delegate: BehaviorDelegate<Webservice>)
    : Webservice {

    override fun getCustomers(): Call<List<Customer>> {
        return delegate.returningResponse(getDummyLiveTables()).getCustomers()
    }

    override fun getTables(): Call<List<Boolean>> {
        return delegate.returningResponse(getDummyLiveBooleans()).getTables()
    }
}