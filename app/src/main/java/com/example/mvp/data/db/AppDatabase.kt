package com.example.mvp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.example.mvp.data.db.model.Book

@Database(entities = [Book::class],version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun BookDao():BookDao
    companion object{
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "Sample.db")
                .build()
    }
}