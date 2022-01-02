package com.example.myfinance

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfinance.database.ExpenseViewModel
import com.example.myfinance.database.ExpenseViewModelFactory
import com.example.myfinance.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    var binding: ActivityMainBinding? = null
    private val newExpenseActivityRequestCode = 1
    private val expenseViewModel: ExpenseViewModel by viewModels {
        ExpenseViewModelFactory((application as ExpenseApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ExpenseListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        expenseViewModel.allExpenses.observe(this) { expenses ->
            expenses.let { adapter.submitList(it) }
        }

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewExpenseActivity::class.java)
            startActivityForResult(intent, newExpenseActivityRequestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newExpenseActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val amount = data?.getStringExtra(NewExpenseActivity.EXTRA_AMOUNT)?.toInt()
            val place = data?.getStringExtra(NewExpenseActivity.EXTRA_PLACE)
            val date = LocalDate.parse(data?.getStringExtra(NewExpenseActivity.EXTRA_DATE))
            val category = data?.getStringExtra(NewExpenseActivity.EXTRA_CATEGORY)
            Log.d(
                TAG,
                "onActivityResult: values from fields = {amount=$amount, place=$place, date=$date, category=$category}"
            )
            if (amount != null && place != null && category != null) {
                expenseViewModel.insert(
                    Expense(
                        amount = amount,
                        place = place,
                        date = date,
                        category = category
                    )
                )
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}