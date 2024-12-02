package com.carsot.serpientes_y_escaleras_app.model

import android.widget.TextView

data class Casilla (
    val Numero: Int,
    val Tipo: TipoCasilla,
    val destino: Int,
    val lugar: TextView
)

enum class TipoCasilla{
    GANADOR, SERPIENTE, ESCALERA, NORMAL
}