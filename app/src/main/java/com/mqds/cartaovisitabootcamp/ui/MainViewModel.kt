package com.mqds.cartaovisitabootcamp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mqds.cartaovisitabootcamp.data.BusinessCard
import com.mqds.cartaovisitabootcamp.data.BusinessCardRepository

class MainViewModel(private val businessCardRepository: BusinessCardRepository): ViewModel() {
    fun insert(businessCard: BusinessCard){
        businessCardRepository.insert(businessCard)
    }

    fun getAll() : LiveData<List<BusinessCard>>{
        return businessCardRepository.getAll()
    }
}

/*

"Inheritance from an interface with '@JvmDefault' members is only allowed with -Xjvm-default option"
https://stackoverflow.com/questions/70992947/how-do-i-resolve-error-message-inheritance-from-an-interface-with-jvmdefault
https://blog.jetbrains.com/kotlin/2020/07/kotlin-1-4-m3-generating-default-methods-in-interfaces/

tasks.withType(KotlinCompile).configureEach {
    kotlinOptions {
        freeCompilerArgs += [
            "-Xjvm-default=all",
        ]
    }
}

*/

class MainViewModelFactory(private val repository: BusinessCardRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}