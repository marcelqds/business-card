package com.mqds.cartaovisitabootcamp

import android.app.Application
import com.mqds.cartaovisitabootcamp.data.AppDatabase
import com.mqds.cartaovisitabootcamp.data.BusinessCardRepository

class App: Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { BusinessCardRepository(database.businessDao())}
}