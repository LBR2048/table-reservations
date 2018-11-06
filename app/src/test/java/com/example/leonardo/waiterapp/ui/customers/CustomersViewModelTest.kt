package com.example.leonardo.waiterapp.ui.customers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.leonardo.waiterapp.MyApplication
import com.example.leonardo.waiterapp.data.customers.CustomersRepository
import com.example.leonardo.waiterapp.mock
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class CustomersViewModelTest {

    private val application = mock(MyApplication::class.java)
    private val repository = mock(CustomersRepository::class.java)
    private val viewModel = CustomersViewModel(application, repository)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test fun getCustomers() {
        // Given
        val customer1 = Customer(1, "firstName1", "lastName1")
        val customer2 = Customer(1, "firstName2", "lastName2")
        val customer3 = Customer(1, "firstName3", "lastName3")
        val customers = listOf(customer1, customer2, customer3)
        val dummyLiveTables = MutableLiveData<List<Customer>>()
        dummyLiveTables.postValue(customers)
        `when`(repository.getCustomers()).thenReturn(dummyLiveTables)

        // When
        viewModel.customers
        viewModel.state

        // Then
        verify(repository).getCustomers()
        val observer = mock<Observer<List<Customer>>>()
        viewModel.customers.observeForever(observer)
        verify(observer).onChanged(customers)
    }
}