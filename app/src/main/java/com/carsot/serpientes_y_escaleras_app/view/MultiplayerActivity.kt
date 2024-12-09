package com.carsot.serpientes_y_escaleras_app.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.carsot.serpientes_y_escaleras_app.R
import com.carsot.serpientes_y_escaleras_app.databinding.ActivityMultiplayerBinding
import com.carsot.serpientes_y_escaleras_app.viewmodel.MultiplayerViewModel

class MultiplayerActivity : AppCompatActivity() {

    // ViewBinding
    private lateinit var binding: ActivityMultiplayerBinding

    // ViewModel
    private val multiplayerViewModel: MultiplayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Asignar el binding
        binding = ActivityMultiplayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observamos los cambios en el estado del juego
        multiplayerViewModel.gameState.observe(this) { game ->
            // Actualiza la UI con la información de la partida
            // Aquí puedes actualizar el tablero, los jugadores, el turno, etc.
        }

        // Observamos los mensajes de error o éxito
        multiplayerViewModel.errorMessage.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        // Manejar el botón de crear partida
        binding.btnCreateGame.setOnClickListener {
            val username = binding.etUsername.text.toString()
            multiplayerViewModel.createGame(username)
        }

        // Manejar el botón de unirse a la partida
        binding.btnJoinGame.setOnClickListener {
            val gameCode = binding.etGameCode.text.toString()
            val username = binding.etUsername.text.toString()
            multiplayerViewModel.joinGame(gameCode, username)
        }
    }
}