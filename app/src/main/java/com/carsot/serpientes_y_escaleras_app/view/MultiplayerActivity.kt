package com.carsot.serpientes_y_escaleras_app.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.carsot.serpientes_y_escaleras_app.databinding.ActivityMultiplayerBinding

class MultiplayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMultiplayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el layout usando View Binding
        binding = ActivityMultiplayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar botón para unirse a una partida
        binding.btnJoinGame.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val gameCode = binding.etGameCode.text.toString()
            if (username.isNotEmpty() && gameCode.isNotEmpty()) {
                // Lógica para unirse a una partida
                Toast.makeText(this, "Unirse a la partida: $gameCode", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar botón para crear una partida
        binding.btnCreateGame.setOnClickListener {
            val username = binding.etUsername.text.toString()
            if (username.isNotEmpty()) {
                // Lógica para crear una nueva partida
                Toast.makeText(this, "Partida creada por: $username", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Ingresa un nombre de usuario", Toast.LENGTH_SHORT).show()
            }
        }
    }
}