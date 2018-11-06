package com.example.leonardo.waiterapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.leonardo.waiterapp.ui.customers.Customer
import com.example.leonardo.waiterapp.ui.customers.CustomersFragment
import com.example.leonardo.waiterapp.ui.tables.TablesFragment
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity(),
        CustomersFragment.OnCustomerInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            showCustomersFragment()
        }
    }

    private fun showCustomersFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, CustomersFragment.newInstance(1))
                .commitNow()
    }

    private fun showTablesFragment(customer: Customer) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, TablesFragment.newInstance(customer.id))
                .addToBackStack(null)
                .commit()
    }

    override fun onCustomerSelected(customer: Customer?) {
        customer?.let {
            showTablesFragment(customer)
        }
    }
}
