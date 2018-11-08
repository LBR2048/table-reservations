package com.example.leonardo.waiterapp.data.tables

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.leonardo.waiterapp.MockInjectorUtils
import com.example.leonardo.waiterapp.dummyTables
import com.example.leonardo.waiterapp.getDummyLiveTables
import com.example.leonardo.waiterapp.mock
import com.example.leonardo.waiterapp.ui.tables.Table
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import java.util.concurrent.Executor

class TablesRepositoryTest {

    private val tablesDao = mock(TablesDao::class.java)
    private val webservice = MockInjectorUtils.getMockWebservice()
    private val executor = Mockito.mock(Executor::class.java)
    private val repository = TablesRepository(tablesDao, webservice, executor)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test fun getTablesFromLocalSource() {
        // Given
        val tables = dummyTables
        val liveTables = getDummyLiveTables()
        `when`(tablesDao.getAll()).thenReturn(liveTables)

        // When
        repository.getTables()

        // Then
        verify(tablesDao).getAll()
        val observerTables = mock<Observer<List<Table>>>()
        repository.getTables().observeForever(observerTables)
        verify(observerTables).onChanged(tables)
    }

    // TODO Add test for the webservice
    // How to wait for the asynchronous response from the mock server?
}