package com.example.leonardo.waiterapp.ui.customers

import android.content.Context
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

class CustomersFragment : androidx.fragment.app.Fragment() {

    private lateinit var viewModel: CustomersViewModel
    private var columnCount = 1
    private var customersAdapter: CustomersAdapter? = null
    private var listener: OnCustomerInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_customers_tables, container, false)

        activity?.title = getString(R.string.customers)

        // Set the adapter
        if (view is androidx.recyclerview.widget.RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> androidx.recyclerview.widget.LinearLayoutManager(context)
                    else -> androidx.recyclerview.widget.GridLayoutManager(context, columnCount)
                }
                customersAdapter = CustomersAdapter(ArrayList(), listener)
                adapter = customersAdapter
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        subscribeToViewModel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCustomerInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnCustomerInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun subscribeToViewModel() {
        activity?.let {
            val factory = InjectorUtils.provideCustomerViewModelFactory(it.application)
            viewModel = ViewModelProviders.of(this, factory).get(CustomersViewModel::class.java)

            viewModel.customers.observe(this, Observer<List<Customer>> { customers ->
                if (customers != null) {
                    customersAdapter?.replaceCustomers(customers)
                }
            })

            viewModel.state.observe(this, Observer<LoadingState> { state ->
                when (state) {
                    LoadingState.LOADING -> Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
                    LoadingState.SUCCESS -> Toast.makeText(context, "Data available", Toast.LENGTH_SHORT).show()
                    LoadingState.FAILURE -> Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    interface OnCustomerInteractionListener {
        fun onCustomerSelected(customer: Customer?)
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        fun newInstance(columnCount: Int) =
                CustomersFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}
