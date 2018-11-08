package com.example.leonardo.waiterapp.data.customers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.leonardo.waiterapp.MockInjectorUtils
import com.example.leonardo.waiterapp.dummyCustomers
import com.example.leonardo.waiterapp.getDummyLiveCustomers
import com.example.leonardo.waiterapp.mock
import com.example.leonardo.waiterapp.ui.customers.Customer
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import java.util.concurrent.Executor

class CustomersRepositoryTest {

    private val customersDao = Mockito.mock(CustomersDao::class.java)
    private val webservice = MockInjectorUtils.getMockWebservice()
    private val executor = Mockito.mock(Executor::class.java)
    private val repository = CustomersRepository(customersDao, webservice, executor)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test fun getCustomersFromLocalSource() {
        // Given
        val customers = dummyCustomers
        val liveCustomers = getDummyLiveCustomers()
        `when`(customersDao.getAll()).thenReturn(liveCustomers)

        // When
        repository.getCustomers()

        // Then
        verify(customersDao).getAll()
        val observerCustomers = mock<Observer<List<Customer>>>()
        repository.getCustomers().observeForever(observerCustomers)
        verify(observerCustomers).onChanged(customers)
    }

    // TODO Add test for the webservice
    // How to wait for the asynchronous response from the mock server?
}