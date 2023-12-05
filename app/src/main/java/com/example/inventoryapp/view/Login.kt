package com.example.inventoryapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher

import com.example.inventoryapp.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //se utiliza viewbinding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //bindings y listeners
        binding.emailText.addTextChangedListener(TextWatcher)
        binding.passwordText.addTextChangedListener(TextWatcher)
        binding.btnLogin.setOnClickListener{
        }
        binding.registro.setOnClickListener {
        }

    }
    //Variable textWatcher que obtiene el estado en vivo de cada InputText para realizar acciones
    private var TextWatcher = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            //If para medir longitud de contraseña
            if (binding.passwordText.text!!.length<6){
                binding.passLayout.error = "La contraseña debe ser mayor a 6 caracteres"
            }else if(binding.passwordText.text!!.length>10){
                binding.passLayout.error = "La contraseña debe ser menor a 10 caracteres"
            }else{
                binding.passLayout.error = null
            }

            //parte encargada de habilitar botones de login y registro
            val email = binding.emailText.text.toString().trim()
            val pass = binding.passwordText.text.toString().trim()
            binding.btnLogin.isEnabled = email.isNotEmpty() && pass.isNotEmpty() && binding.passLayout.error==null
            binding.registro.isEnabled = email.isNotEmpty() && pass.isNotEmpty() && binding.passLayout.error==null
        }
        override fun afterTextChanged(s: Editable?) {
        }
    }


}

