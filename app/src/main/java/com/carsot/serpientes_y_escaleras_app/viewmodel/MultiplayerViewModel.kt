package com.carsot.serpientes_y_escaleras_app.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.carsot.serpientes_y_escaleras_app.model.Game
import com.carsot.serpientes_y_escaleras_app.model.Jugador
import com.carsot.serpientes_y_escaleras_app.model.Casilla
import com.carsot.serpientes_y_escaleras_app.model.TipoCasilla
import kotlin.random.Random
import com.google.firebase.firestore.FieldValue

class MultiplayerViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _gameState = MutableLiveData<Game>()
    val gameState: LiveData<Game> get() = _gameState

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _dados = MutableLiveData<Pair<Int, Int>>()
    val dados: LiveData<Pair<Int, Int>> get() = _dados

    // Crear una nueva partida
    fun createGame(username: String) {
        if (username.isEmpty()) {
            _errorMessage.value = "Por favor, ingresa tu nombre"
            return
        }

        // Generar un código de juego único
        val gameCode = generateGameCode()

        // Crear el documento de la partida en Firestore
        val game = hashMapOf(
            "gameCode" to gameCode,
            "player1" to hashMapOf("username" to username, "userId" to auth.currentUser?.uid),
            "player2" to null, // Segundo jugador aún no se ha unido
            "board" to getInitialBoard(),
            "turn" to "player1", // El primer jugador inicia
            "player1Position" to 1, // Posición inicial del primer jugador
            "player2Position" to 1, // Posición inicial del segundo jugador (aún no existe)
            "diceRolls" to 0 // Contador de dados
        )

        db.collection("games")
            .document(gameCode)
            .set(game)
            .addOnSuccessListener {
                _errorMessage.value = "Partida creada con éxito! Código de juego: $gameCode"
            }
            .addOnFailureListener { e ->
                _errorMessage.value = "Error al crear la partida: ${e.message}"
            }
    }

    // Unirse a una partida
    fun joinGame(gameCode: String, username: String) {
        if (gameCode.isEmpty() || username.isEmpty()) {
            _errorMessage.value = "Por favor, ingresa todos los campos"
            return
        }

        val gameRef = db.collection("games").document(gameCode)

        // Intentar obtener la partida
        gameRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                val game = document.toObject(Game::class.java)

                if (game?.player2 == null) {
                    // Si la partida no tiene un segundo jugador, añadir al jugador 2
                    gameRef.update(
                        "player2", hashMapOf("username" to username, "userId" to auth.currentUser?.uid)
                    )
                    _errorMessage.value = "Te has unido a la partida!"
                } else {
                    _errorMessage.value = "La partida ya está llena"
                }
            } else {
                _errorMessage.value = "Código de partida inválido"
            }
        }.addOnFailureListener {
            _errorMessage.value = "Error al intentar unirse a la partida"
        }
    }

    // Función para generar un código de juego único
    private fun generateGameCode(): String {
        return "GAME_${System.currentTimeMillis()}"
    }

    // Función para obtener el tablero inicial
    private fun getInitialBoard(): List<Int> {
        return List(100) { 0 }
    }

    // Escuchar los cambios en la partida en tiempo real
    fun listenToGame(gameCode: String) {
        val gameRef = db.collection("games").document(gameCode)

        gameRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("MultiplayerViewModel", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                val game = snapshot.toObject(Game::class.java)

                // Verificar si el objeto game no es nulo
                game?.let {
                    _gameState.value = it
                }
            }
        }
    }

    // Función para tirar los dados
    fun rollDice(gameCode: String) {
        val dice1 = Random.nextInt(1, 7)
        val dice2 = Random.nextInt(1, 7)
        _dados.value = Pair(dice1, dice2)

        // Actualizar los dados en Firebase
        val gameRef = db.collection("games").document(gameCode)

        gameRef.update(
            "diceRolls", FieldValue.increment(1),
            "player1Position", dice1 + dice2 // Esto sería un ejemplo de cómo mover al jugador, adáptalo a tu lógica.
        )
    }

    // Actualizar el turno
    fun updateTurn(gameCode: String, currentTurn: String) {
        val nextTurn = if (currentTurn == "player1") "player2" else "player1"

        val gameRef = db.collection("games").document(gameCode)

        gameRef.update(
            "turn", nextTurn
        )
    }

    // Procesar el movimiento
    fun processMove(gameCode: String, playerPosition: Int) {
        val gameRef = db.collection("games").document(gameCode)

        gameRef.update(
            "player1Position", playerPosition // Actualiza la posición del jugador en Firebase
        )
    }
}