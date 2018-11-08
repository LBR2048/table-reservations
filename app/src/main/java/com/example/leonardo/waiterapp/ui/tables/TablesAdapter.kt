package com.example.leonardo.waiterapp.ui.tables

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.leonardo.waiterapp.R
import kotlinx.android.synthetic.main.fragment_customer_item.view.*

class TablesAdapter(
        val customerId: Int,
        private val values: MutableList<Table>,
        private val fragment: OnAdapterInteractionListener)
    : androidx.recyclerview.widget.RecyclerView.Adapter<TablesAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Table
            fragment.onTableSelected(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_table_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        with(holder.tableNumberView) {
            text = item.id.toString()
            setBackgroundResource(
                    when {
                        item.customerId == customerId -> R.drawable.table_selected_border
                        item.available -> R.drawable.table_available_border
                        else -> R.drawable.table_unavailable_border
                    }
            )
        }
        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val mView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mView) {
        val tableNumberView: TextView = mView.table_number

        override fun toString(): String {
            return super.toString() + " '" + tableNumberView.text + "'"
        }
    }

    // TODO Use Diffutil. I tried, but the adapter did not update correctly
    fun replaceCustomers(tables: List<Table>) {
        values.apply {
            clear()
            addAll(tables)
        }
        notifyDataSetChanged()
    }

    interface OnAdapterInteractionListener {
        fun onTableSelected(table: Table)
    }
}
