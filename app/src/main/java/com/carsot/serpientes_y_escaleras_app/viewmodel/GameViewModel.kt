package com.carsot.serpientes_y_escaleras_app.viewmodel

import android.content.Context
import android.speech.tts.TextToSpeech
import android.widget.TextView
import android.widget.Toast
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

    private lateinit var mTTS: TextToSpeech

    init {
        _mapa.value = mutableListOf()
        _jugadores.value = mutableListOf(
            Jugador(1, "Jugador 1", true, 1, 0),
            Jugador(2, "Jugador 2", false, 1, 0)
        )
        actualizarTurno()
    }

    fun inicializarJuego(casillas: List<Pair<Int, TextView>>, tts: TextToSpeech) {
        mTTS = tts
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

    fun tirarDados(context: Context, TV1: TextView, TV2: TextView, textoGanador: TextView) {
        val resultado = Random.nextInt(6) + 1
        val resultado2 = Random.nextInt(6) + 1
        _dados.value = Pair(resultado, resultado2)

        // Mostrar resultados de los dados en los TextView
        TV1.text = resultado.toString()
        TV2.text = resultado2.toString()

        // Procesar movimiento
        procesarMovimiento(context, resultado, resultado2, textoGanador)
    }

    private fun procesarMovimiento(context: Context, resultado: Int, resultado2: Int, textoGanador: TextView) {
        val jugadores = _jugadores.value ?: return
        val mapa = _mapa.value ?: return
        val jugadorActual = jugadores.firstOrNull { it.turno } ?: return
        val jugadorOponente = jugadores.firstOrNull { !it.turno } ?: return

        if (jugadorActual.contDado == 3) {
            resetPosicionJugador(context, jugadorActual)
            return
        }

        var posAnterior = jugadorActual.Posicion
        jugadorActual.Posicion += resultado + resultado2

        if (jugadorActual.Posicion > 64) {
            showToast(context, "Lo siento, no fue suficiente para llegar a la meta!!")
            jugadorActual.Posicion = posAnterior
        } else {
            for (casilla in mapa) {
                if (casilla.Numero == posAnterior) {
                    if (casilla.lugar?.text.toString() == "$posAnterior.XO") {
                        casilla.lugar?.text = "$posAnterior.O"
                    } else {
                        casilla.lugar?.text = "$posAnterior.  "
                    }
                }

                if (casilla.Numero == jugadorActual.Posicion) {
                    when (casilla.Tipo) {
                        TipoCasilla.ESCALERA -> {
                            posAnterior = jugadorActual.Posicion
                            jugadorActual.Posicion = casilla.destino
                        }
                        TipoCasilla.SERPIENTE -> {
                            posAnterior = jugadorActual.Posicion
                            jugadorActual.Posicion = casilla.destino
                        }
                        TipoCasilla.GANADOR -> {
                            if (casilla.lugar?.text.toString() == "${jugadorActual.Posicion}.X ") {
                                casilla.lugar?.text = "${jugadorActual.Posicion}.XO"
                                val textoParaLeer = "${jugadorActual.Posicion} Congratulations, you win!"
                                mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                            } else {
                                casilla.lugar?.text = "${jugadorActual.Posicion}.O "
                                val textoParaLeer = "${jugadorActual.Posicion} Congratulations, you win!"
                                mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                            }
                            textoGanador.text = "${textoGanador.text}\n${jugadorActual.nombre}"
                            jugadorActual.turno = false
                            jugadorOponente.turno = false
                            _ganador.value = jugadorActual.nombre
                            return
                        }
                        else -> {
                            if (casilla.lugar?.text.toString() == "${jugadorActual.Posicion}.O") {
                                casilla.lugar?.text = "${jugadorActual.Posicion}.XO"
                            } else {
                                casilla.lugar?.text = "${jugadorActual.Posicion}.X "
                            }
                        }
                    }
                }
            }
        }

        actualizarCasilla(mapa.firstOrNull { it?.Numero == jugadorActual.Posicion }, jugadorActual, jugadores.firstOrNull { it.id != jugadorActual.id })

        // Cambio de turno
        val suma = resultado + resultado2
        if (suma == 6) {
            jugadorActual.turno = true
            jugadorOponente.turno = false
            jugadorActual.contDado += 1
            showToast(context, "Vamos tus dados suman 6, ¡Tienes otro intento!")
        } else {
            jugadorActual.turno = false
            jugadorOponente.turno = true
            jugadorActual.contDado = 0
        }

        _jugadores.value = jugadores
        actualizarTurno()
    }

    private fun resetPosicionJugador(context: Context, jugador: Jugador) {
        showToast(context, "Lo siento, ya sacaste tres veces seis con ambos dados!!")

        val mapa = _mapa.value ?: return // Asegúrate de que `_mapa.value` no sea nulo

        // Iterar sobre las casillas del mapa
        for (casilla in mapa) {
            if (casilla?.Numero == jugador.Posicion) {
                casilla.lugar?.text = when {
                    casilla.lugar?.text.toString() == "${jugador.Posicion}.XO" -> "${jugador.Posicion}.O"
                    casilla.lugar?.text.toString() == "${jugador.Posicion}.X " -> "${jugador.Posicion}.  "
                    else -> casilla.lugar?.text.toString()
                }
            }
        }

        // Reiniciar la posición del jugador
        jugador.Posicion = 1

        for (casilla in mapa) {
            if (casilla?.Numero == 1) {
                casilla.lugar?.text = when {
                    casilla.lugar?.text.toString() == "1.O" -> "1.XO"
                    casilla.lugar?.text.toString() == "1.  " -> "1.X "
                    else -> casilla.lugar?.text.toString()
                }
            }
        }

        // Acceder a la lista de jugadores desde el LiveData
        val jugadoresLista = _jugadores.value ?: mutableListOf()

        // Buscar al jugador oponente
        val jugadorOponente = jugadoresLista.firstOrNull { it.id != jugador.id }

        // Buscar la casilla correspondiente
        val casillaJugador = mapa.firstOrNull { it?.Numero == jugador.Posicion }

        if (casillaJugador != null && jugadorOponente != null) {
            actualizarCasilla(casillaJugador, jugador, jugadorOponente)
        }
    }

    private fun showToast(context: Context, message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast.show()
    }

    private fun actualizarCasilla(casilla: Casilla?, jugadorActual: Jugador, jugadorOponente: Jugador?) {
        if (casilla == null) return // Verificar nulabilidad de `casilla`

        casilla.lugar?.text = when {
            casilla.Numero == jugadorActual.Posicion && casilla.Numero == jugadorOponente?.Posicion -> "${casilla.Numero}.XO"
            casilla.Numero == jugadorActual.Posicion -> "${casilla.Numero}.X"
            casilla.Numero == jugadorOponente?.Posicion -> "${casilla.Numero}.O"
            else -> "${casilla.Numero}. "
        }

        val textoParaLeer = "Casilla ${casilla.Numero}"
        mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
    }
}
