package com.example.leonardo.waiterapp.ui.customers

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.leonardo.waiterapp.R
import kotlinx.android.synthetic.main.fragment_customer_item.view.*

class CustomersAdapter(
        private val mValues: MutableList<Customer>,
        private val mListener: CustomersFragment.OnCustomerInteractionListener?)
    : androidx.recyclerview.widget.RecyclerView.Adapter<CustomersAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Customer
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onCustomerSelected(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_customer_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.customerNameView.text = item.toString()

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    fun replaceCustomers(customers: List<Customer>) {
        mValues.apply {
            clear()
            addAll(customers)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mView) {
        val customerNameView: TextView = mView.table_number

        override fun toString(): String {
            return super.toString() + " '" + customerNameView.text + "'"
        }
    }
}
