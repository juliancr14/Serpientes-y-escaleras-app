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
import com.google.firebase.firestore.FirebaseFirestore
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

    //Firestore DB
    private val db = FirebaseFirestore.getInstance();

    init {
        _mapa.value = mutableListOf()
        _jugadores.value = mutableListOf(
            Jugador(1, "Jugador 1", true, 1, 0, "X"), // Jugador 1 con ficha "X"
            Jugador(2, "Jugador 2", false, 1, 0, "O") // Jugador 2 con ficha "O"
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
            mTTS.speak("No fue suficiente para llegar a la meta", TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            // Limpiar la casilla anterior
            mapa.firstOrNull { it.Numero == posAnterior }?.let { casillaAnterior ->
                casillaAnterior.lugar?.text = when {
                    casillaAnterior.lugar?.text.toString() == "$posAnterior.${jugadorActual.simbolo}${jugadorOponente.simbolo}" ->
                        "$posAnterior.${jugadorOponente.simbolo}" // Solo queda el oponente
                    else -> "$posAnterior.  " // La casilla queda vacía
                }
            }

            // Obtener la casilla actual del jugador después del movimiento
            var casillaActual = mapa.firstOrNull { it.Numero == jugadorActual.Posicion }

            // Verificar si el jugador cayó en una serpiente o escalera
            if (casillaActual?.Tipo == TipoCasilla.ESCALERA || casillaActual?.Tipo == TipoCasilla.SERPIENTE) {
                val tipo = if (casillaActual.Tipo == TipoCasilla.ESCALERA) "escalera" else "serpiente"
                val destino = casillaActual.destino
                mTTS.speak(
                    "¡Cuidado! Has caído en una $tipo. Ahora te mueves a la casilla $destino.",
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    null
                )
                posAnterior = jugadorActual.Posicion // Guardar posición antes del cambio
                jugadorActual.Posicion = casillaActual.destino
                casillaActual = mapa.firstOrNull { it.Numero == jugadorActual.Posicion } // Actualizar a la nueva posición
            }

            // Actualizar casilla actual después del movimiento
            casillaActual?.let { casilla ->
                casilla.lugar?.text = when {
                    casilla.Numero == jugadorActual.Posicion && casilla.Numero == jugadorOponente?.Posicion ->
                        "${casilla.Numero}.${jugadorActual.simbolo}${jugadorOponente.simbolo}" // Ambos jugadores están en la casilla
                    casilla.Numero == jugadorActual.Posicion ->
                        "${casilla.Numero}.${jugadorActual.simbolo}" // Solo el jugador actual está en la casilla
                    else -> "${casilla.Numero}.  " // La casilla queda vacía
                }
                mTTS.speak("Casilla ${casilla.Numero}.", TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }

        // Verificar tipo de casilla final (ganadora)
        val nuevaCasilla = mapa.firstOrNull { it?.Numero == jugadorActual.Posicion }
        if (nuevaCasilla?.Tipo == TipoCasilla.GANADOR) {
            textoGanador.text = "${textoGanador.text}\n${jugadorActual.nombre}"
            showToast(context, "¡Felicidades ${jugadorActual.nombre}, has ganado!")
            mTTS.speak("¡Felicidades ${jugadorActual.nombre}, has ganado!", TextToSpeech.QUEUE_FLUSH, null, null)
            _ganador.value = jugadorActual.nombre

            //Obtener fecha y hora de la victoria
            val fechaActual = java.time.LocalDate.now()

            //Guardar nombre del ganador en la base de datos
            db.collection("winners").document(jugadorActual.nombre).set(
                hashMapOf("User" to jugadorActual.nombre,
                        "Date" to fechaActual.toString()
                )
            )

            return
        }

        // Cambio de turno
        val suma = resultado + resultado2
        if (suma == 6) {
            jugadorActual.turno = true
            jugadorActual.contDado += 1
            showToast(context, "¡Sacaste un 6, tienes otro turno!")
            mTTS.speak("Sacaste un 6, tienes otro turno.", TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            jugadorActual.turno = false
            jugadorOponente.turno = true
            jugadorActual.contDado = 0
        }

        _jugadores.value = jugadores
        actualizarTurno()
    }

    private fun actualizarCasilla(casilla: Casilla?, jugadorActual: Jugador, jugadorOponente: Jugador?) {
        if (casilla == null) return

        casilla.lugar?.text = when {
            casilla.Numero == jugadorActual.Posicion && casilla.Numero == jugadorOponente?.Posicion ->
                "${casilla.Numero}.${jugadorActual.simbolo}${jugadorOponente.simbolo}" // Ambos jugadores comparten la casilla
            casilla.Numero == jugadorActual.Posicion ->
                "${casilla.Numero}.${jugadorActual.simbolo}" // Solo el jugador actual está en la casilla
            casilla.Numero == jugadorOponente?.Posicion ->
                "${casilla.Numero}.${jugadorOponente.simbolo}" // Solo el jugador oponente está en la casilla
            else -> "${casilla.Numero}.  " // La casilla está vacía
        }

        // Texto hablado de la casilla para el jugador actual
        val textoParaLeer = "Casilla ${casilla.Numero}"
        mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
    }

}
