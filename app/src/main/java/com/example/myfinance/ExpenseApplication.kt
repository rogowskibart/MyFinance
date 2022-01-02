package com.example.myfinance

import android.app.Application
import com.example.myfinance.database.ExpenseRepository
import com.example.myfinance.database.ExpenseRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ExpenseApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { ExpenseRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ExpenseRepository(database.expenseDao()) }
}