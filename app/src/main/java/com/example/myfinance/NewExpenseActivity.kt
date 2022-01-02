package com.example.myfinance

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class NewExpenseActivity : AppCompatActivity() {

    private lateinit var editAmountView: EditText
    private lateinit var editPlaceView: EditText
    private lateinit var editDateView: EditText
    private lateinit var editCategoryView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_expense)

        editAmountView = findViewById(R.id.amount_et)
        editPlaceView = findViewById(R.id.place_et)
        editDateView = findViewById(R.id.date_et)
        editCategoryView = findViewById(R.id.category_et)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editAmountView.text) ||
                TextUtils.isEmpty(editPlaceView.text) ||
                TextUtils.isEmpty(editDateView.text) ||
                TextUtils.isEmpty(editCategoryView.text)
            ) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val amount = editAmountView.text.toString()
                val place = editPlaceView.text.toString()
                val date = editDateView.text.toString()
                val category = editCategoryView.text.toString()

                replyIntent.putExtra(EXTRA_AMOUNT, amount)
                replyIntent.putExtra(EXTRA_PLACE, place)
                replyIntent.putExtra(EXTRA_DATE, date)
                replyIntent.putExtra(EXTRA_CATEGORY, category)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_AMOUNT = "com.example.myfinance.AMOUNT"
        const val EXTRA_PLACE = "com.example.myfinance.PLACE"
        const val EXTRA_DATE = "com.example.myfinance.DATE"
        const val EXTRA_CATEGORY = "com.example.myfinance.CATEGORY"
    }
}