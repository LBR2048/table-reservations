package com.example.leonardo.waiterapp.ui.tables

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.leonardo.waiterapp.MyApplication
import com.example.leonardo.waiterapp.data.tables.TablesRepository
import com.example.leonardo.waiterapp.mock
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import java.util.*

class TablesViewModelTest {

    private val application = mock(MyApplication::class.java)
    private val repository = mock(TablesRepository::class.java)
    private val viewModel = TablesViewModel(application, repository)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test fun getTables() {
        // Given
        val table1 = Table(1, true)
        val table2 = Table(2, false)
        val table3 = Table(3, false, 4)
        val tables = listOf(table1, table2, table3)
        val dummyLiveTables = MutableLiveData<List<Table>>()
        dummyLiveTables.postValue(tables)
        `when`(repository.getTables()).thenReturn(dummyLiveTables)

        // When
        viewModel.tables

        // Then
        verify(repository).getTables()
        val observerTables = mock<Observer<List<Table>>>()
        viewModel.tables.observeForever(observerTables)
        verify(observerTables).onChanged(tables)
    }

    @Test fun selectAvailableTable() {
        // Given a table is available
        val customerId = 1
        val availableTable = Table(2, true)

        // When it is selected
        val viewModel = TablesViewModel(application, repository)
        viewModel.selectTable(availableTable, customerId)

        // Then it is reserved for the current customer
        verify(repository, times(1))
                .makeReservation(availableTable, customerId)
        verifyNoMoreInteractions(repository)
    }

    @Test fun selectTableReservedForCurrentCustomer() {
        // Given a table is already reserved for the current customer
        val customerId = 1
        val availableTable = Table(2, false, customerId)

        // When it is selected
        val viewModel = TablesViewModel(application, repository)
        viewModel.selectTable(availableTable, customerId)

        // Then the reservation is cancelled
        verify(repository, times(1)).removeReservation(customerId)
        verifyNoMoreInteractions(repository)
    }

    @Test fun selectOccupiedTable() {
        // Given a table is occupied
        val customerId = 1
        val occupiedTable = Table(2, false)

        // When it is selected
        val viewModel = TablesViewModel(application, repository)
        viewModel.selectTable(occupiedTable, customerId)

        // Then no interactions occur with the repository
        verifyZeroInteractions(repository)
    }
}