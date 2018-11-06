package com.example.leonardo.waiterapp.ui.customers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.leonardo.waiterapp.MyApplication
import com.example.leonardo.waiterapp.data.customers.CustomersRepository
import com.example.leonardo.waiterapp.dummyCustomers
import com.example.leonardo.waiterapp.getDummyLiveCustomers
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
        val customers = dummyCustomers
        val liveCustomers = getDummyLiveCustomers()
        `when`(repository.getCustomers()).thenReturn(liveCustomers)

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