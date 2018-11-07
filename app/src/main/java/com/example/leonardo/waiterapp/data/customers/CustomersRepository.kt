package com.example.leonardo.waiterapp.data.customers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.leonardo.waiterapp.data.LoadingState
import com.example.leonardo.waiterapp.data.Webservice
import com.example.leonardo.waiterapp.ui.customers.Customer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor

class CustomersRepository(
        private val customersDao: CustomersDao,
        private val webService: Webservice,
        private val executor: Executor) {

    var state = MutableLiveData<LoadingState>()

    fun getCustomers(): LiveData<List<Customer>> {
        getCustomersFromRemoteSource()
        return getCustomersFromLocalSource()
    }

    private fun getCustomersFromRemoteSource() {
        state.value = LoadingState.LOADING

        webService.getCustomers().enqueue(object : Callback<List<Customer>> {
            override fun onResponse(call: Call<List<Customer>>, response: Response<List<Customer>>) {
                state.value = LoadingState.SUCCESS

                val customers = response.body()
                if (customers != null) {
                    saveCustomersToLocalSource(customers)
                }
            }

            override fun onFailure(call: Call<List<Customer>>, t: Throwable) {
                state.value = LoadingState.FAILURE
            }
        })
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