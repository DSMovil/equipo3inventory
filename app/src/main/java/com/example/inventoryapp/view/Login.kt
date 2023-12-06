package com.example.inventoryapp.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

import com.example.inventoryapp.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val userEmail = sharedPreferences.getString("user_email", null)

        if (userEmail != null) {
            // Ya hay una sesión guardada, inicia directamente la actividad principal
            val intent = Intent(this@Login, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        //se utiliza viewbinding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //bindings y listeners
        binding.emailText.addTextChangedListener(TextWatcher)
        binding.passwordText.addTextChangedListener(TextWatcher)
        binding.btnLogin.setOnClickListener{
            val email = binding.emailText.text.toString().trim()
            val pass = binding.passwordText.text.toString().trim()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                authenticate(email, pass)
            } else {
                // Manejar el caso en el que los campos estén vacíos
                Toast.makeText(this@Login, "Ingresa email y contraseña", Toast.LENGTH_SHORT).show()
            }
        }
        binding.registro.setOnClickListener {
            val email = binding.emailText.text.toString().trim()
            val pass = binding.passwordText.text.toString().trim()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                register(email, pass)
            } else {
                // Manejar el caso en el que los campos estén vacíos
                Toast.makeText(this@Login, "Ingresa email y contraseña", Toast.LENGTH_SHORT).show()
            }
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
            if (email.isNotEmpty() && pass.isNotEmpty() && binding.passLayout.error==null){
                binding.registro.setTextColor(Color.WHITE)
                binding.registro.setTypeface(null, Typeface.BOLD)
            }else{
                binding.registro.setTextColor(Color.GRAY)
                binding.registro.setTypeface(null, Typeface.NORMAL)
            }

        }
        override fun afterTextChanged(s: Editable?) {
        }
    }
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private fun authenticate(email: String, password: String): Boolean {
        try {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // La autenticación fue exitosa, puedes realizar otras acciones aquí
                    Toast.makeText(this@Login, "Login Correcto", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    val intent1 = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent1)
                    finish()

// Guardar información del usuario en SharedPreferences
                    val sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("user_email", email)
                    editor.apply()
                } else {
                    // La autenticación falló, muestra un Toast con el mensaje de error
                    Toast.makeText(this@Login, "Login incorrecto", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            // Manejo de excepciones, si es necesario
            return false
        }

        return false
    }

    private fun register(email: String, password: String) {
        try {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // El registro fue exitoso, puedes realizar otras acciones aquí
                    Toast.makeText(this@Login, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@Login, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // El registro falló, muestra un Toast con el mensaje de error
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        // Maneja el caso cuando ya existe un usuario con el mismo email
                        Toast.makeText(this@Login, "Error en el registro: Este email ya está registrado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@Login, "Error en el registro: No es un email válido", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: Exception) {
            // Manejo de excepciones, si es necesario
            Toast.makeText(this@Login, "Error al intentar registrarse", Toast.LENGTH_SHORT).show()
        }
    }


}

