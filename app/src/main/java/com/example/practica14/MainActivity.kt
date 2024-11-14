package com.example.practica14

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicialización de FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Referencias a los campos de email y contraseña y el botón de login
        val emailEditText = findViewById<EditText>(R.id.EmailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passeditText)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        // Configurar el listener para el botón de login
        btnLogin.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Realizar el login con Firebase Authentication
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this){ task ->
                if (task.isSuccessful) {
                    // Si la autenticación es exitosa, vamos a la actividad de bienvenida
                    val intent = Intent(this, welcomeActivity::class.java)
                    startActivity(intent)
                    finish() // Si deseas cerrar esta actividad después de hacer login.
                } else {
                    // Si ocurre un error en la autenticación, muestra un mensaje
                    Toast.makeText(this, "Autenticación fallida", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

