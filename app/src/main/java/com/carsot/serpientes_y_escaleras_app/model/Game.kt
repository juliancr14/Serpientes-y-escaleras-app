package com.carsot.serpientes_y_escaleras_app.model

data class Game(
    val gameCode: String = "",
    val player1: Player? = null,
    val player2: Player? = null,
    val board: List<Int> = emptyList(),
    val turn: String = ""
)

data class Player(
    val username: String = "",
    val userId: String? = null
)