package com.example.leonardo.waiterapp.ui.customers

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.leonardo.waiterapp.data.customers.CustomersRepository

class CustomersViewModelFactory(
        private val application: Application,
        private val repository: CustomersRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CustomersViewModel(application, repository) as T
    }
}