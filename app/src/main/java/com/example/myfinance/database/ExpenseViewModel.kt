package com.example.myfinance.database

import androidx.lifecycle.*
import com.example.myfinance.Expense
import kotlinx.coroutines.launch

class ExpenseViewModel(private val repository: ExpenseRepository) : ViewModel() {
    val allExpenses: LiveData<List<Expense>> = repository.allExpenses.asLiveData()

    fun insert(expense: Expense) = viewModelScope.launch {
        repository.insert(expense)
    }
}

class ExpenseViewModelFactory(private val repository: ExpenseRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExpenseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ExpenseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}