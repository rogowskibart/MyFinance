package com.example.myfinance.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myfinance.Expense
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
import java.util.*

@Database(entities = arrayOf(Expense::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
public abstract class ExpenseRoomDatabase : RoomDatabase() {


    abstract fun expenseDao(): ExpenseDao

    private class ExpenseDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        private val TAG = "ExpenseDatabaseCallback"

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.expenseDao())
                }
            }
        }

        suspend fun populateDatabase(expenseDao: ExpenseDao) {
            expenseDao.deleteAll()

            val date1 = LocalDate.of(2021, Month.DECEMBER, 1)
            val date2 = LocalDate.of(2021, Month.DECEMBER, 2)
            val date3 = LocalDate.of(2021, Month.DECEMBER, 3)
            val expense1 =
                Expense(
                    amount = 12,
                    place = "Biedronka",
                    date = date1,
                    category = "Food"
                )
            val expense2 =
                Expense(
                    amount = 22,
                    place = "McD",
                    date = date2,
                    category = "Food"
                )
            val expense3 =
                Expense(
                    amount = 31,
                    place = "KFC",
                    date = date3,
                    category = "Food"
                )
            Log.d(TAG, "populateDatabase: inserting $expense1")
            expenseDao.insert(expense1)
            expenseDao.insert(expense2)
            expenseDao.insert(expense3)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ExpenseRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ExpenseRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpenseRoomDatabase::class.java,
                    "expense_database"
                )
                    .addCallback(ExpenseDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
