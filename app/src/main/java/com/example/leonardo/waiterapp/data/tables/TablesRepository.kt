package com.example.leonardo.waiterapp.data.tables

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.leonardo.waiterapp.data.LoadingState
import com.example.leonardo.waiterapp.data.Webservice
import com.example.leonardo.waiterapp.ui.tables.Table
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.concurrent.Executor

class TablesRepository(
        private val tablesDao: TablesDao,
        private val webservice: Webservice,
        private val executor: Executor) {

    var state = MutableLiveData<LoadingState>()

    fun getTables(): LiveData<List<Table>> {
        getTablesFromRemoteSource()
        return getTablesFromLocalSource()
    }

    private fun getTablesFromRemoteSource() {
        state.value = LoadingState.LOADING

        webservice.getTables().enqueue(object : Callback<List<Boolean>> {
            override fun onResponse(call: Call<List<Boolean>>, response: Response<List<Boolean>>) {
                state.value = LoadingState.SUCCESS

                val body = response.body()
                if (body != null) {
                    saveTablesToLocalSource(booleansToTables(body))
                }
            }

            override fun onFailure(call: Call<List<Boolean>>, t: Throwable) {
                state.value = LoadingState.FAILURE
            }
        })
    }

    private fun saveTablesToLocalSource(tables: List<Table>) {
        executor.execute {
            tablesDao.insertAll(tables)
        }
    }

    private fun getTablesFromLocalSource(): LiveData<List<Table>> {
        return tablesDao.getAll()
    }

    private fun booleansToTables(booleans: List<Boolean>): List<Table> {
        var tableId = 0
        val tables = ArrayList<Table>()

        booleans.map {
            tables.add(Table(tableId++, it, null))
        }

        return tables
    }

    fun makeReservation(table: Table, customerId: Int) {
        // Delete current reservation from customer
        removeReservation(customerId)

        executor.execute {
            // Create new reservation for customer
            table.available = false
            table.customerId = customerId
            tablesDao.insert(table)
        }
    }

    fun removeReservation(customerId: Int) {
        executor.execute {
            val tablesByCustomer = tablesDao.getTablesByCustomerSync(customerId)
            tablesByCustomer.map {
                it.available = true
                it.customerId = null
                tablesDao.insert(it)
            }
        }
    }
}