package com.example.inventoryapp.view

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        //bindings y listeners
        binding.codigoText.addTextChangedListener(TextWatcher)
        binding.precioTexto.addTextChangedListener(TextWatcher)
        binding.cantidadText.addTextChangedListener(TextWatcher)
        binding.articuloText.addTextChangedListener(TextWatcher)

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
}