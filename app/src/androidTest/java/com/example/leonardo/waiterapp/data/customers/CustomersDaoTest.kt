package com.example.leonardo.waiterapp.data.customers

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.leonardo.waiterapp.data.AppDatabase
import com.example.leonardo.waiterapp.ui.customers.Customer
import com.example.leonardo.waiterapp.data.getValue
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

    // Given these customers to be used in all tests
    private val customer1 = Customer(1, "First1", "Last1")
    private val customer2 = Customer(2, "First2", "Last2")
    private val customer3 = Customer(3, "First3", "Last3")
    private val newCustomer3 = Customer(3, "First3New", "Last3New")

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
        // When
        val customers = listOf(customer2, customer1, customer3, newCustomer3)
        customerDao.insertAll(customers)
        val list = getValue(customerDao.getAll())

        // Then
        assertThat(list[0], equalTo(customer1))
        assertThat(list[1], equalTo(customer2))
        assertThat(list[2], equalTo(customer3)) // customer3 should not have been replaced
    }
}