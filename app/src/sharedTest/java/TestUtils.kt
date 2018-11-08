package com.example.leonardo.waiterapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.leonardo.waiterapp.ui.customers.Customer
import com.example.leonardo.waiterapp.ui.tables.Table

val dummyCustomer1 = Customer(1, "firstName1", "lastName1")
val dummyCustomer2 = Customer(2, "firstName2", "lastName2")
val dummyCustomer3 = Customer(3, "firstName3", "lastName3")
val dummyCustomers = listOf(dummyCustomer1, dummyCustomer2, dummyCustomer3)

fun getDummyLiveCustomers(): LiveData<List<Customer>> {
    val liveCustomers = MutableLiveData<List<Customer>>()
    liveCustomers.postValue(dummyCustomers)
    return liveCustomers
}

val dummyTable1 = Table(1, true)
val dummyTable2 = Table(2, false)
val dummyTable3 = Table(3, false)
val dummyTables = listOf(dummyTable1, dummyTable3, dummyTable2)
val emptyTableList: List<Table> = emptyList()

fun getDummyLiveTables(): LiveData<List<Table>> {
    val liveTables = MutableLiveData<List<Table>>()
    liveTables.postValue(dummyTables)
    return liveTables
}

fun getEmptyLiveTables(): LiveData<List<Table>> {
    return MutableLiveData()
}

private val booleans = listOf(true, true, true, false, false)

fun getDummyLiveBooleans(): LiveData<List<Boolean>> {
    val dummyLiveBooleans = MutableLiveData<List<Boolean>>()
    dummyLiveBooleans.postValue(booleans)
    return dummyLiveBooleans
}