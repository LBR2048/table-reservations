package com.example.leonardo.waiterapp.ui.tables

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.leonardo.waiterapp.data.tables.TablesRepository

class TablesViewModelFactory(
        private val application: Application,
        private val repository: TablesRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TablesViewModel(application, repository) as T
    }
}