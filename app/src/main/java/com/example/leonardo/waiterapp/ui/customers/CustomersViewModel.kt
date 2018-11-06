package com.example.leonardo.waiterapp.ui.customers

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.leonardo.waiterapp.data.LoadingState
import com.example.leonardo.waiterapp.data.customers.CustomersRepository

class CustomersViewModel(application: Application,
                         private val repository: CustomersRepository)
    : AndroidViewModel(application) {

    val state: LiveData<LoadingState> by lazy { repository.state }
    val customers: LiveData<List<Customer>> by lazy { repository.getCustomers() }
}
