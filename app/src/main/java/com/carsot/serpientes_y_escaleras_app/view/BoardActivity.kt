package com.carsot.serpientes_y_escaleras_app.view

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.carsot.serpientes_y_escaleras_app.R
import com.carsot.serpientes_y_escaleras_app.viewmodel.GameViewModel
import java.util.Locale

class BoardActivity : AppCompatActivity() {
    private lateinit var mTTS: TextToSpeech
    private val viewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        // Inicializar Text-to-Speech
        mTTS = TextToSpeech(this) {
            mTTS.language = Locale.getDefault()
        }

        // Obtener referencias a los elementos de la interfaz de usuario
        val txtJname = findViewById<TextView>(R.id.txtTurno)
        val TV1 = findViewById<TextView>(R.id.dado)
        val TV2 = findViewById<TextView>(R.id.dadodos)
        val textoGanador: TextView = findViewById<TextView>(R.id.txtGanador)

        // Pasar los nombres de los jugadores al ViewModel
        val name1: String? = intent.getStringExtra("Nombre1")
        val name2: String? = intent.getStringExtra("Nombre2")
        viewModel.jugadores.observe(this) {
            if (it.isNotEmpty()) {
                if (name1 != null && name2 != null) {
                    it[0].nombre = name1
                    it[1].nombre = name2
                }
            }
        }

        // Inicializar las casillas del juego
        val casillas = listOf(
            Pair(1, findViewById<TextView>(R.id.casilla1)),
            Pair(2, findViewById<TextView>(R.id.casilla2)),
            Pair(3, findViewById<TextView>(R.id.casilla3)),
            Pair(4, findViewById<TextView>(R.id.casilla4)),
            Pair(5, findViewById<TextView>(R.id.casilla5)),
            Pair(6, findViewById<TextView>(R.id.casilla6)),
            Pair(7, findViewById<TextView>(R.id.casilla7)),
            Pair(8, findViewById<TextView>(R.id.casilla8)),
            Pair(9, findViewById<TextView>(R.id.casilla9)),
            Pair(10, findViewById<TextView>(R.id.casilla10)),
            Pair(11, findViewById<TextView>(R.id.casilla11)),
            Pair(12, findViewById<TextView>(R.id.casilla12)),
            Pair(13, findViewById<TextView>(R.id.casilla13)),
            Pair(14, findViewById<TextView>(R.id.casilla14)),
            Pair(15, findViewById<TextView>(R.id.casilla15)),
            Pair(16, findViewById<TextView>(R.id.casilla16)),
            Pair(17, findViewById<TextView>(R.id.casilla17)),
            Pair(18, findViewById<TextView>(R.id.casilla18)),
            Pair(19, findViewById<TextView>(R.id.casilla19)),
            Pair(20, findViewById<TextView>(R.id.casilla20)),
            Pair(21, findViewById<TextView>(R.id.casilla21)),
            Pair(22, findViewById<TextView>(R.id.casilla22)),
            Pair(23, findViewById<TextView>(R.id.casilla23)),
            Pair(24, findViewById<TextView>(R.id.casilla24)),
            Pair(25, findViewById<TextView>(R.id.casilla25)),
            Pair(26, findViewById<TextView>(R.id.casilla26)),
            Pair(27, findViewById<TextView>(R.id.casilla27)),
            Pair(28, findViewById<TextView>(R.id.casilla28)),
            Pair(29, findViewById<TextView>(R.id.casilla29)),
            Pair(30, findViewById<TextView>(R.id.casilla30)),
            Pair(31, findViewById<TextView>(R.id.casilla31)),
            Pair(32, findViewById<TextView>(R.id.casilla32)),
            Pair(33, findViewById<TextView>(R.id.casilla33)),
            Pair(34, findViewById<TextView>(R.id.casilla34)),
            Pair(35, findViewById<TextView>(R.id.casilla35)),
            Pair(36, findViewById<TextView>(R.id.casilla36)),
            Pair(37, findViewById<TextView>(R.id.casilla37)),
            Pair(38, findViewById<TextView>(R.id.casilla38)),
            Pair(39, findViewById<TextView>(R.id.casilla39)),
            Pair(40, findViewById<TextView>(R.id.casilla40)),
            Pair(41, findViewById<TextView>(R.id.casilla41)),
            Pair(42, findViewById<TextView>(R.id.casilla42)),
            Pair(43, findViewById<TextView>(R.id.casilla43)),
            Pair(44, findViewById<TextView>(R.id.casilla44)),
            Pair(45, findViewById<TextView>(R.id.casilla45)),
            Pair(46, findViewById<TextView>(R.id.casilla46)),
            Pair(47, findViewById<TextView>(R.id.casilla47)),
            Pair(48, findViewById<TextView>(R.id.casilla48)),
            Pair(49, findViewById<TextView>(R.id.casilla49)),
            Pair(50, findViewById<TextView>(R.id.casilla50)),
            Pair(51, findViewById<TextView>(R.id.casilla51)),
            Pair(52, findViewById<TextView>(R.id.casilla52)),
            Pair(53, findViewById<TextView>(R.id.casilla53)),
            Pair(54, findViewById<TextView>(R.id.casilla54)),
            Pair(55, findViewById<TextView>(R.id.casilla55)),
            Pair(56, findViewById<TextView>(R.id.casilla56)),
            Pair(57, findViewById<TextView>(R.id.casilla57)),
            Pair(58, findViewById<TextView>(R.id.casilla58)),
            Pair(59, findViewById<TextView>(R.id.casilla59)),
            Pair(60, findViewById<TextView>(R.id.casilla60)),
            Pair(61, findViewById<TextView>(R.id.casilla61)),
            Pair(62, findViewById<TextView>(R.id.casilla62)),
            Pair(63, findViewById<TextView>(R.id.casilla63)),
            Pair(64, findViewById<TextView>(R.id.casilla64))
        )
        viewModel.inicializarJuego(casillas)

        // Observar los cambios en el turno, los dados y el ganador
        viewModel.turno.observe(this) { turno ->
            txtJname.text = turno
        }

        viewModel.dados.observe(this) { dados ->
            TV1.text = dados.first.toString()
            TV2.text = dados.second.toString()
        }

        viewModel.ganador.observe(this) { ganador ->
            ganador?.let {
                textoGanador.text = "GANADOR: $ganador"
            }
        }

        // Configurar el botón de retorno
        val btn: ImageButton = findViewById(R.id.btnReturn)
        btn.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Configurar el botón para tirar los dados
        val btn2: Button = findViewById(R.id.botonDado)
        btn2.setOnClickListener {
            viewModel.tirarDados()
        }
    }
}
