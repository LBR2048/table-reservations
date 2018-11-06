package com.example.leonardo.waiterapp.data.tables

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.leonardo.waiterapp.data.AppDatabase
import com.example.leonardo.waiterapp.data.getValue
import com.example.leonardo.waiterapp.dummyTable1
import com.example.leonardo.waiterapp.dummyTable2
import com.example.leonardo.waiterapp.dummyTable3
import com.example.leonardo.waiterapp.ui.tables.Table
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
        // Given
        val newTable3 = Table(3, true)
        val tables = listOf(dummyTable2, dummyTable1, dummyTable3, newTable3)

        // When
        tablesDao.insertAll(tables)
        val list = getValue(tablesDao.getAll())

        // Then
        assertThat(list[0], equalTo(dummyTable1))
        assertThat(list[1], equalTo(dummyTable2))
        assertThat(list[2], equalTo(dummyTable3)) // table3 should not have been replaced
    }

    @Test fun insertAndRetrieveCustomers() {
        // Given
        val newTable3 = Table(3, true)

        // When
        tablesDao.insert(dummyTable1)
        tablesDao.insert(dummyTable3)
        tablesDao.insert(newTable3)
        tablesDao.insert(dummyTable2)
        val list = getValue(tablesDao.getAll())

        // Then
        assertThat(list[0], equalTo(dummyTable1))
        assertThat(list[1], equalTo(dummyTable2))
        assertThat(list[2], equalTo(newTable3)) // table3 should have been replaced
    }
}