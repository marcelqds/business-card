package com.mqds.cartaovisitabootcamp.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper


@Database(entities = [BusinessCard::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun businessDao() : IBusinessCardDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bussiness_db").build()
                INSTANCE = instance
                instance
            }
        }
    }

}