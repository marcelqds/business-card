package com.mqds.cartaovisitabootcamp.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.mqds.cartaovisitabootcamp.App
import com.mqds.cartaovisitabootcamp.databinding.ActivityMainBinding
import com.mqds.cartaovisitabootcamp.util.Image

class MainActivity : AppCompatActivity() {
    //    private val binding by lazy {ActivityMainBinding.inflate(layoutInflate)}
    companion object{
        private val REQUEST_PERMISSION_STORAGE = 10
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var cardSelected : View
    private val adapter by lazy { BusinessCardAdapter() }
    private val mainViewModel: MainViewModel by viewModels{
        MainViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvCards.adapter = adapter
        getAllBusinessCard()
        openAddListener()
    }

    private fun openAddListener(){
        binding.btOpenAdd.setOnClickListener{
            val intent = Intent(this@MainActivity,AddBusinessCardActivity::class.java)
            startActivity(intent)
        }

        adapter.listenerShare = { card ->
            cardSelected = card;
            checkPermissions()
        }
    }

    private fun shareImage(){
        Image.share(this@MainActivity,cardSelected)
    }

    private fun getAllBusinessCard(){
        mainViewModel.getAll().observe(this,{ businessCards ->
            adapter.submitList(businessCards)
        })
    }

    private fun checkPermissions(){
        val perRead:String = Manifest.permission.READ_EXTERNAL_STORAGE;
        val perWrite = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        val checkPermission = PackageManager.PERMISSION_GRANTED
        if(ActivityCompat.checkSelfPermission(this,perRead) != checkPermission ||
        ActivityCompat.checkSelfPermission(this,perWrite) != checkPermission){
            val permissions = arrayOf(perRead,perWrite)
            ActivityCompat.requestPermissions(this,permissions, REQUEST_PERMISSION_STORAGE)
        }else{
            shareImage()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.size > 1  && requestCode == REQUEST_PERMISSION_STORAGE){
            shareImage()
        }
    }

}