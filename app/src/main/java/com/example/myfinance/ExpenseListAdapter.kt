package com.example.myfinance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.exp

class ExpenseListAdapter :
    ListAdapter<Expense, ExpenseListAdapter.ExpenseViewHolder>(ExpenseComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        return ExpenseViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val id = getItem(position).id
        val currentAmount = getItem(position).amount
        val currentPlace = getItem(position).place
        val currentDate = getItem(position).date
        val currentCategory = getItem(position).category
        holder.bind(Expense(id, currentAmount, currentPlace, currentDate, currentCategory))
    }

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val amountItemView: TextView = itemView.findViewById(R.id.amount_tv)
        private val placeItemView: TextView = itemView.findViewById(R.id.place_tv)
        private val dateItemView: TextView = itemView.findViewById(R.id.date_tv)
        private val categoryItemView: TextView = itemView.findViewById(R.id.category_tv)

        fun bind(expense: Expense?) {
            amountItemView.text = expense?.amount.toString()
            placeItemView.text = expense?.place
            dateItemView.text = expense?.date.toString()
            categoryItemView.text = expense?.category
        }

        companion object {
            fun create(parent: ViewGroup): ExpenseViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return ExpenseViewHolder(view)
            }
        }
    }

    class ExpenseComparator : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            var x = true
            when {
                oldItem.amount != newItem.amount -> {
                    x = false
                }
                oldItem.category != newItem.category -> {
                    x = false
                }
                oldItem.date != newItem.date -> {
                    x = false
                }
                oldItem.place != newItem.place -> {
                    x = false
                }
            }
            return x
        }
    }
}