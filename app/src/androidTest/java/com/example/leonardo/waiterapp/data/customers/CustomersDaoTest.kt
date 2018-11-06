package com.example.leonardo.waiterapp.data.customers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.leonardo.waiterapp.data.AppDatabase
import com.example.leonardo.waiterapp.data.getValue
import com.example.leonardo.waiterapp.dummyCustomer1
import com.example.leonardo.waiterapp.dummyCustomer2
import com.example.leonardo.waiterapp.dummyCustomer3
import com.example.leonardo.waiterapp.ui.customers.Customer
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CustomersDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var customerDao: CustomersDao

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before fun createDatabase() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        customerDao = database.customerDao()
    }

    @After fun closeDatabase() {
        database.close()
    }

    @Test fun insertAndRetrieveCustomers() {
        // Given
        val newCustomer3 = Customer(3, "First3New", "Last3New")
        val customers = listOf(dummyCustomer2, dummyCustomer1, dummyCustomer3, newCustomer3)

        // When
        customerDao.insertAll(customers)

        // Then
        val list = getValue(customerDao.getAll())
        assertThat(list[0], equalTo(dummyCustomer1))
        assertThat(list[1], equalTo(dummyCustomer2))
        assertThat(list[2], equalTo(dummyCustomer3)) // dummyCustomer3 should not have been replaced
    }
}