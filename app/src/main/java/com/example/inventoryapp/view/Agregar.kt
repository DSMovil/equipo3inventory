package com.example.inventoryapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.inventoryapp.R
import com.example.inventoryapp.databinding.ActivityAgregarBinding
import com.example.inventoryapp.databinding.ActivityLoginBinding
import com.example.inventoryapp.databinding.ActivityMainBinding

class Agregar : AppCompatActivity() {
    private lateinit var binding: ActivityAgregarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar)


        //se utiliza viewbinding
        binding = ActivityAgregarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarElementos.setNavigationOnClickListener {
            val intent = Intent(this@Agregar, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent = Intent(this@Agregar, MainActivity::class.java)
                startActivity(intent)
                finish()
            }


        })



    }
}