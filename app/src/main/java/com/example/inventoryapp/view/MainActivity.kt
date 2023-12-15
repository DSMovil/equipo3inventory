package com.example.inventoryapp.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.inventoryapp.R
import com.example.inventoryapp.databinding.ActivityLoginBinding
import com.example.inventoryapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var productoArrayList: ArrayList<Producto>
    private lateinit var adapter: Adapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se utiliza viewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)  // Usar el root de la vista inflada

        var auth = FirebaseAuth.getInstance()

        recyclerView = binding.recyclerViewInventario
        progressBar = binding.progressBar
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        productoArrayList = arrayListOf()
        adapter = Adapter(productoArrayList)
        recyclerView.adapter = adapter

        EventChangeListener()

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

    private fun EventChangeListener() {
        showProgressBar()
        db = FirebaseFirestore.getInstance()
        db.collection("productos").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("firestore error", error.message.toString())
                    // Añade un Toast para mostrar el mensaje de error en el dispositivo
                    Toast.makeText(this@MainActivity, "Error en Firestore: ${error.message}", Toast.LENGTH_SHORT).show()
                    return
                }

                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        productoArrayList.add(dc.document.toObject(Producto::class.java))
                    }
                }

                adapter.notifyDataSetChanged()
            }
        })
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }

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