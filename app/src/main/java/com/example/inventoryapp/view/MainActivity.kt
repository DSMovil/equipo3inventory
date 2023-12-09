package com.example.inventoryapp.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.inventoryapp.R
import com.example.inventoryapp.databinding.ActivityLoginBinding
import com.example.inventoryapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import android.appwidget.AppWidgetManager
import android.content.ComponentName

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

        binding.btnAgregar.setOnClickListener{

            val intent = Intent(this@MainActivity, Agregar::class.java)
            startActivity(intent)
            finish()

        }
    }
/*
    private fun cambiarFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer,fragment)
        fragmentTransaction.commit()
    }

 */

    private fun addWidgetToHomeScreen() {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val myWidget = ComponentName(this, Mi_widget::class.java)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(myWidget)

        val intent = Intent(this, Mi_widget::class.java)
        intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)
        sendBroadcast(intent)
    }
}