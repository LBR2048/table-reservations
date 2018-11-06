package com.example.leonardo.waiterapp.ui.tables

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.leonardo.waiterapp.data.LoadingState
import com.example.leonardo.waiterapp.data.tables.TablesRepository

open class TablesViewModel(
        application: Application,
        private val repository: TablesRepository)
    : AndroidViewModel(application) {

    val state: LiveData<LoadingState> by lazy { repository.state }
    val tables: LiveData<List<Table>> by lazy { repository.getTables() }

    fun selectTable(table: Table, customerId: Int) {
        when {
            table.available -> makeReservation(table, customerId)
            table.customerId == customerId -> removeReservation(customerId)
        }
    }

    private fun makeReservation(table: Table, customerId: Int) {
        repository.makeReservation(table, customerId)
    }

    private fun removeReservation(customerId: Int) {
        repository.removeReservation(customerId)
    }

}
