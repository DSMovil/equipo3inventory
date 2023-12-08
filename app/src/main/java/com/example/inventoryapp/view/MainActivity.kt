package com.example.inventoryapp.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.inventoryapp.R
import com.example.inventoryapp.databinding.ActivityLoginBinding
import com.example.inventoryapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var auth = FirebaseAuth.getInstance()

        //se utiliza viewbinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //boton cerrar sesion
        binding.logout.setOnClickListener{
            auth.signOut()
            val intent = Intent(this@MainActivity, Login::class.java)
            startActivity(intent)
            finish()
            val sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
        }
    }
}