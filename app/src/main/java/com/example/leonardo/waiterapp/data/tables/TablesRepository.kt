package com.example.leonardo.waiterapp.data.tables

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.leonardo.waiterapp.data.AppDatabase
import com.example.leonardo.waiterapp.data.LoadingState
import com.example.leonardo.waiterapp.data.Webservice
import com.example.leonardo.waiterapp.ui.tables.Table
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.Executors

class TablesRepository(
        private val appDatabase: AppDatabase,
        private val webservice: Webservice) {

    var state = MutableLiveData<LoadingState>()

    // TODO Executor is the best approach? Should it be injected?
    private val executor = Executors.newSingleThreadExecutor()

    fun getTables(): LiveData<List<Table>> {
        getTablesFromRemoteSource()
        return getTablesFromLocalSource()
    }

    private fun getTablesFromRemoteSource(): LiveData<List<Table>> {
        state.value = LoadingState.LOADING
        var tableId = 0
        val tables = ArrayList<Table>()

        val data = MutableLiveData<List<Table>>()
        webservice.getTables().enqueue(object : Callback<List<Boolean>> {
            override fun onResponse(call: Call<List<Boolean>>, response: Response<List<Boolean>>) {
                state.value = LoadingState.SUCCESS
                response.body()?.map {
                    tables.add(Table(tableId++, it, null))
                }
                data.value = tables
                data.value?.let {
                    saveTablesToLocalSource(it)
                }
            }

            override fun onFailure(call: Call<List<Boolean>>, t: Throwable) {
                state.value = LoadingState.FAILURE
            }
        })
        return data
    }

    private fun saveTablesToLocalSource(tables: List<Table>) {
        executor.execute {
            appDatabase.tablesDao().insertAll(tables)
        }
    }

    private fun getTablesFromLocalSource(): LiveData<List<Table>> {
        return appDatabase.tablesDao().getAll()
    }

    fun makeReservation(table: Table, customerId: Int) {
        // Delete current reservation from customer
        removeReservation(customerId)

        executor.execute {
            // Create new reservation for customer
            table.available = false
            table.customerId = customerId
            appDatabase.tablesDao().insert(table)
        }
    }

    fun removeReservation(customerId: Int) {
        executor.execute {
            val tablesByCustomer = appDatabase.tablesDao().getTablesByCustomerSync(customerId)
            tablesByCustomer.map {
                it.available = true
                it.customerId = null
                appDatabase.tablesDao().insert(it)
            }
        }
    }
}