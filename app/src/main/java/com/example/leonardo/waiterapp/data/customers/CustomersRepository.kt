package com.example.leonardo.waiterapp.data.customers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.leonardo.waiterapp.data.LoadingState
import com.example.leonardo.waiterapp.data.Webservice
import com.example.leonardo.waiterapp.ui.customers.Customer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class CustomersRepository(
        private val customersDao: CustomersDao,
        private val webService: Webservice) {

    var state = MutableLiveData<LoadingState>()
    private val executor = Executors.newSingleThreadExecutor()

    fun getCustomers(): LiveData<List<Customer>> {
        getCustomersFromRemoteSource()
        return getCustomersFromLocalSource()
    }

    private fun getCustomersFromRemoteSource(): LiveData<List<Customer>> {
        state.value = LoadingState.LOADING
        val data = MutableLiveData<List<Customer>>()
        webService.getCustomers().enqueue(object : Callback<List<Customer>> {
            override fun onResponse(call: Call<List<Customer>>, response: Response<List<Customer>>) {
                state.value = LoadingState.SUCCESS
                data.value = response.body()
                data.value?.let {
                    saveCustomersToLocalSource(it)
                }
            }

            override fun onFailure(call: Call<List<Customer>>, t: Throwable) {
                state.value = LoadingState.FAILURE
            }
        })
        return data
    }

    private fun saveCustomersToLocalSource(customers: List<Customer>) {
        executor.execute {
            customersDao.insertAll(customers)
        }
    }

    private fun getCustomersFromLocalSource(): LiveData<List<Customer>> {
        return customersDao.getAll()
    }
}