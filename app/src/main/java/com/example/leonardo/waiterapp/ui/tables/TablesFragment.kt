package com.example.leonardo.waiterapp.ui.tables

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.leonardo.waiterapp.InjectorUtils
import com.example.leonardo.waiterapp.R
import com.example.leonardo.waiterapp.data.LoadingState
import com.example.leonardo.waiterapp.data.LoadingState.*

class TablesFragment : androidx.fragment.app.Fragment(), TablesAdapter.OnAdapterInteractionListener {

    private lateinit var viewModel: TablesViewModel
    private var customerId: Int = 0
    private val columnCount = COLUMN_COUNT
    private var tablesAdapter: TablesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            customerId = it.getInt(ARG_CUSTOMER_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_customers_tables, container, false)

        activity?.title = getString(R.string.tables)

        // Set the adapter
        if (view is androidx.recyclerview.widget.RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> androidx.recyclerview.widget.LinearLayoutManager(context)
                    else -> androidx.recyclerview.widget.GridLayoutManager(context, COLUMN_COUNT)
                }
                tablesAdapter = TablesAdapter(customerId, ArrayList(), this@TablesFragment)
                adapter = tablesAdapter
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        subscribeToViewModel()
    }

    override fun onTableSelected(table: Table) {
        viewModel.selectTable(table, customerId)
    }

    private fun subscribeToViewModel() {
        activity?.let {
            val factory = InjectorUtils.provideTablesViewModelFactory(it.application)
            viewModel = ViewModelProviders.of(this, factory).get(TablesViewModel::class.java)

            viewModel.tables.observe(this, Observer<List<Table>> { tables ->
                if (tables != null) {
                    tablesAdapter?.replaceCustomers(tables)
                }
            })

            viewModel.state.observe(this, Observer<LoadingState> { state ->
                when (state) {
                    LOADING -> Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                    SUCCESS -> Toast.makeText(context, "Data available", Toast.LENGTH_SHORT).show()
                    FAILURE -> Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    companion object {

        const val ARG_CUSTOMER_ID = "arg-customer-id"
        const val COLUMN_COUNT = 3

        fun newInstance(customerId: Int) =
                TablesFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_CUSTOMER_ID, customerId)
                    }
                }
    }
}
