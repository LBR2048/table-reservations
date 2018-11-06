package com.example.leonardo.waiterapp.data.tables

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.leonardo.waiterapp.data.AppDatabase
import com.example.leonardo.waiterapp.ui.tables.Table
import com.example.leonardo.waiterapp.data.getValue
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TablesDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var tablesDao: TablesDao

    // Given these tables to be used in all tests
    private val table1 = Table(1, true)
    private val table2 = Table(2, false)
    private val table3 = Table(3, false) // TODO cannot insert table with customerId FK
    private val newTable3 = Table(3, true)

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before fun createDatabase() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        tablesDao = database.tablesDao()
    }

    @After fun closeDatabase() {
        database.close()
    }

    @Test fun insertAllAndRetrieveCustomers() {
        // When
        val tables = listOf(table2, table1, table3, newTable3)
        tablesDao.insertAll(tables)
        val list = getValue(tablesDao.getAll())

        // Then
        assertThat(list[0], equalTo(table1))
        assertThat(list[1], equalTo(table2))
        assertThat(list[2], equalTo(table3)) // table3 should not have been replaced
    }

    @Test fun insertAndRetrieveCustomers() {
        // When
        tablesDao.insert(table1)
        tablesDao.insert(table3)
        tablesDao.insert(newTable3)
        tablesDao.insert(table2)
        val list = getValue(tablesDao.getAll())

        // Then
        assertThat(list[0], equalTo(table1))
        assertThat(list[1], equalTo(table2))
        assertThat(list[2], equalTo(newTable3)) // table3 should have been replaced
    }
}