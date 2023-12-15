package com.example.inventoryapp.view

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.inventoryapp.R
import com.example.inventoryapp.databinding.ActivityAgregarBinding
import com.example.inventoryapp.databinding.ActivityLoginBinding
import com.example.inventoryapp.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class Agregar : AppCompatActivity() {
    private lateinit var binding: ActivityAgregarBinding
    val db = Firebase.firestore
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
        //Cambiar direccion de boton atras en esta actividad
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent = Intent(this@Agregar, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
        val codigo = binding.codigoText
        val nombre = binding.articuloText
        val precio = binding.precioTexto
        val cantidad = binding.cantidadText
        //bindings y listeners
        binding.codigoText.addTextChangedListener(TextWatcher)
        binding.precioTexto.addTextChangedListener(TextWatcher)
        binding.cantidadText.addTextChangedListener(TextWatcher)
        binding.articuloText.addTextChangedListener(TextWatcher)

        binding.btnGuardar.setOnClickListener {
            guardarDatos(
                codigo.text.toString().toDouble(),
                nombre.text.toString(),
                precio.text.toString().toDouble(),
                cantidad.text.toString().toDouble()
            )
        }
    }

    //Variable textWatcher que obtiene el estado en vivo de cada InputText para realizar acciones
    private var TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            //parte encargada de habilitar botones de login y registro
            val codigo = binding.codigoText.text.toString().trim()
            val nombre = binding.articuloText.text.toString().trim()
            val precio = binding.precioTexto.text.toString().trim()
            val cantidad = binding.cantidadText.text.toString().trim()
            binding.btnGuardar.isEnabled = codigo.isNotEmpty() && nombre.isNotEmpty() && precio.isNotEmpty() && cantidad.isNotEmpty()
        }
        override fun afterTextChanged(s: Editable?) {
        }
    }
    //funcion encargada de almacenar los datos en firestore
    private fun guardarDatos(codigo: Double, nombre: String, precio: Double, cantidad: Double) {
        // hashmap de datos
        val data = hashMapOf(
            "codigo" to codigo,
            "nombre" to nombre,
            "precio" to precio,
            "cantidad" to cantidad
        )
        val collectionRef = db.collection("productos")

        collectionRef
            .add(data)
            .addOnSuccessListener { _ ->
                //acciones a realizar si la operacion es exitosa
                binding.codigoText.text!!.clear()
                binding.articuloText.text!!.clear()
                binding.precioTexto.text!!.clear()
                binding.cantidadText.text!!.clear()
                Toast.makeText(this@Agregar, "Añadido exitosamente", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{_ ->
                Toast.makeText(this@Agregar, "Error al añadir", Toast.LENGTH_SHORT).show()
            }
    }
}