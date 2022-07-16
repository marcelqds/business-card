package com.mqds.cartaovisitabootcamp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mqds.cartaovisitabootcamp.App
import com.mqds.cartaovisitabootcamp.R
import com.mqds.cartaovisitabootcamp.data.BusinessCard
import com.mqds.cartaovisitabootcamp.databinding.ActivityAddBusinessCardBinding

class AddBusinessCardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBusinessCardBinding

    private val mainViewModel: MainViewModel by viewModels{
        MainViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBusinessCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        closeAddNew()
        insertListener()
    }

    private fun closeAddNew(){
        binding.btClose.setOnClickListener{
            //val intent = Intent(this@AddBussinessCardActivity,MainActivity::class.java)
            //startActivity(intent)
            finish()
        }
    }

    private fun insertListener(){
        binding.btAddNew.setOnClickListener{
            val businessCard = BusinessCard(
                name = binding.etName.editText?.text.toString(),
                email = binding.etEmail.editText?.text.toString(),
                phoneNumber = binding.etPhoneNumber.editText?.text.toString(),
                company = binding.etCompany.editText?.text.toString(),
                backgroundColor = binding.etBackgroundColor.editText?.text.toString()
            )

            mainViewModel.insert(businessCard)
            Toast.makeText(this,R.string.txtSuccess,Toast.LENGTH_LONG).show()
            finish()
        }
    }
}