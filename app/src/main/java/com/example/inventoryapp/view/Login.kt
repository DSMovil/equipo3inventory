package com.example.inventoryapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.example.inventoryapp.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.passwordText.doOnTextChanged { text, start, before, count ->
            if (text!!.length<6){
                binding.passLayout.error = "La contraseña debe ser mayor a 6 caracteres"
            }else if(text!!.length>10){
                binding.passLayout.error = "La contraseña debe ser menor a 10 caracteres"
            }else{
                binding.passLayout.error = null
            }
        }
        binding.emailText.addTextChangedListener(TextWatcher)
        binding.passwordText.addTextChangedListener(TextWatcher)

    }
    private var TextWatcher = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val email = binding.emailText.text.toString().trim()
            val pass = binding.passwordText.text.toString().trim()
            binding.btnLogin.isEnabled = email.isNotEmpty() && pass.isNotEmpty() && binding.passLayout.error==null
        }
        override fun afterTextChanged(s: Editable?) {
        }
    }
}

