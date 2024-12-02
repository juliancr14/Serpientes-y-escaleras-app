package com.carsot.serpientes_y_escaleras_app.viewmodel

import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.carsot.serpientes_y_escaleras_app.model.Casilla
import com.carsot.serpientes_y_escaleras_app.model.Jugador
import com.carsot.serpientes_y_escaleras_app.model.TipoCasilla
import kotlin.random.Random

class GameViewModel : ViewModel() {
    private val _mapa = MutableLiveData<MutableList<Casilla>>()
    val mapa: LiveData<MutableList<Casilla>> get() = _mapa

    private val _jugadores = MutableLiveData<MutableList<Jugador>>()
    val jugadores: LiveData<MutableList<Jugador>> get() = _jugadores

    private val _turno = MutableLiveData<String>()
    val turno: LiveData<String> get() = _turno

    private val _dados = MutableLiveData<Pair<Int, Int>>()
    val dados: LiveData<Pair<Int, Int>> get() = _dados

    private val _ganador = MutableLiveData<String?>()
    val ganador: LiveData<String?> get() = _ganador

    init {
        _mapa.value = mutableListOf()
        _jugadores.value = mutableListOf(
            Jugador(1, "Jugador 1", true, 1, 0),
            Jugador(2, "Jugador 2", false, 1, 0)
        )
        actualizarTurno()
    }

    fun inicializarJuego(casillas: List<Pair<Int, TextView>>) {
        val mapaInicial = casillas.map { (numero, textView) ->
            when (numero) {
                2 -> Casilla(numero, TipoCasilla.ESCALERA, 17, textView)
                8 -> Casilla(numero, TipoCasilla.ESCALERA, 39, textView)
                12 -> Casilla(numero, TipoCasilla.ESCALERA, 38, textView)
                31 -> Casilla(numero, TipoCasilla.SERPIENTE, 3, textView)
                32 -> Casilla(numero, TipoCasilla.ESCALERA, 47, textView)
                44 -> Casilla(numero, TipoCasilla.ESCALERA, 61, textView)
                51 -> Casilla(numero, TipoCasilla.SERPIENTE, 5, textView)
                57 -> Casilla(numero, TipoCasilla.SERPIENTE, 38, textView)
                60 -> Casilla(numero, TipoCasilla.SERPIENTE, 43, textView)
                62 -> Casilla(numero, TipoCasilla.SERPIENTE, 48, textView)
                63 -> Casilla(numero, TipoCasilla.SERPIENTE, 50, textView)
                64 -> Casilla(numero, TipoCasilla.GANADOR, 0, textView)
                else -> Casilla(numero, TipoCasilla.NORMAL, 0, textView)
            }
        }.toMutableList()

        _mapa.value = mapaInicial
    }

    fun actualizarTurno() {
        val jugadorActual = _jugadores.value?.firstOrNull { it.turno }
        _turno.value = "Turno: ${jugadorActual?.nombre?.toUpperCase()}"
    }

    fun tirarDados() {
        val resultado = Random.nextInt(6) + 1
        val resultado2 = Random.nextInt(6) + 1
        _dados.value = Pair(resultado, resultado2)
        procesarMovimiento(resultado, resultado2)
    }

    private fun procesarMovimiento(resultado: Int, resultado2: Int) {
        val jugadores = _jugadores.value ?: return
        val mapa = _mapa.value ?: return
        var jugadorActual = jugadores.firstOrNull { it.turno } ?: return
        val jugadorOponente = jugadores.firstOrNull { !it.turno } ?: return

        val posAnterior = jugadorActual.Posicion
        jugadorActual.Posicion += resultado + resultado2

        // Check if player position exceeds the board limits
        if (jugadorActual.Posicion > 64) {
            jugadorActual.Posicion = posAnterior
            return
        }

        // Handle rolling six three times
        if (jugadorActual.contDado == 3) {
            jugadorActual.Posicion = 1
            jugadorActual.contDado = 0
        } else {
            // Check if new position is a snake or ladder
            val nuevaCasilla = mapa.firstOrNull { it.Numero == jugadorActual.Posicion }
            if (nuevaCasilla != null) {
                jugadorActual.Posicion = nuevaCasilla.destino.takeIf { nuevaCasilla.Tipo != TipoCasilla.NORMAL } ?: jugadorActual.Posicion

                // Update TextView for the new position
                nuevaCasilla.lugar.text = "${jugadorActual.Posicion}.XO"
            }
        }

        // Update TextView for the previous position
        val casillaAnterior = mapa.firstOrNull { it.Numero == posAnterior }
        if (casillaAnterior != null) {
            casillaAnterior.lugar.text = when (casillaAnterior.lugar.text) {
                "$posAnterior.XO" -> "$posAnterior.O"
                else -> "$posAnterior. "
            }
        }

        // Check for win condition
        if (jugadorActual.Posicion == 64) {
            _ganador.value = jugadorActual.nombre
            jugadores.forEach { it.turno = false }
            return
        }

        // Handle turn changes
        val suma = resultado + resultado2
        if (suma == 6) {
            jugadorActual.turno = true
            jugadorOponente.turno = false
            jugadorActual.contDado += 1
        } else {
            jugadorActual.turno = false
            jugadorOponente.turno = true
            jugadorActual.contDado = 0
        }

        _jugadores.value = jugadores // Trigger observers
        actualizarTurno() // Update turn display
    }

    fun cambiarTurno() {
        _jugadores.value?.let { jugadores ->
            jugadores.forEach { it.turno = !it.turno }
            actualizarTurno()
        }
    }
}
