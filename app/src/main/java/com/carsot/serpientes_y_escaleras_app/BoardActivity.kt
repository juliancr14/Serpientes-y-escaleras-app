package com.carsot.serpientes_y_escaleras_app

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import kotlin.random.Random

class BoardActivity : AppCompatActivity(){
    private val mapa = mutableListOf<Casilla>()
    private val Jugadores: MutableList<Jugador> = mutableListOf<Jugador>()
    lateinit var mTTS: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        val txtJname = findViewById<TextView>(R.id.txtTurno)
        val TV1 = findViewById<TextView>(R.id.dado)
        val TV2 = findViewById<TextView>(R.id.dadodos)
        val textoGanador: TextView = findViewById<TextView>(R.id.txtGanador)

        val name1: String? = intent.getStringExtra("Nombre1")
        val name2: String? = intent.getStringExtra("Nombre2")

        if (name1 != null && name2 != null){
            Jugadores.add(Jugador(1, name1, true,1,0))
            Jugadores.add(Jugador(2, name2, false,1,0))
        }else{
            Jugadores.add(Jugador(1, "Jugador 1", true,1,0))
            Jugadores.add(Jugador(2, "Jugador 2", false,1,0))
        }

        mTTS = TextToSpeech(this)
        {
            mTTS.language = Locale.getDefault()
        }

        val jugador = Jugadores[0]
        val jugador2= Jugadores[1]

        if (jugador.turno) {
            txtJname.setText(jugador.nombre.toUpperCase())
        }else{
            txtJname.setText(jugador2.nombre.toUpperCase())
        }

        mapa.add(Casilla(1, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla1)))
        mapa.add(Casilla(2, TipoCasilla.ESCALERA, 17, findViewById<TextView>(R.id.casilla2)))
        mapa.add(Casilla(3, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla3)))
        mapa.add(Casilla(4, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla4)))
        mapa.add(Casilla(5, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla5)))
        mapa.add(Casilla(6, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla6)))
        mapa.add(Casilla(7, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla7)))
        mapa.add(Casilla(8, TipoCasilla.ESCALERA, 39, findViewById<TextView>(R.id.casilla8)))
        mapa.add(Casilla(9, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla9)))
        mapa.add(Casilla(10, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla10)))
        mapa.add(Casilla(11, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla11)))
        mapa.add(Casilla(12, TipoCasilla.ESCALERA, 38, findViewById<TextView>(R.id.casilla12)))
        mapa.add(Casilla(13, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla13)))
        mapa.add(Casilla(14, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla14)))
        mapa.add(Casilla(15, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla15)))
        mapa.add(Casilla(16, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla16)))
        mapa.add(Casilla(17, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla17)))
        mapa.add(Casilla(18, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla18)))
        mapa.add(Casilla(19, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla19)))
        mapa.add(Casilla(20, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla20)))
        mapa.add(Casilla(21, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla21)))
        mapa.add(Casilla(22, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla22)))
        mapa.add(Casilla(23, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla23)))
        mapa.add(Casilla(24, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla24)))
        mapa.add(Casilla(25, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla25)))
        mapa.add(Casilla(26, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla26)))
        mapa.add(Casilla(27, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla27)))
        mapa.add(Casilla(28, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla28)))
        mapa.add(Casilla(29, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla29)))
        mapa.add(Casilla(30, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla30)))
        mapa.add(Casilla(31, TipoCasilla.SERPIENTE, 3, findViewById<TextView>(R.id.casilla31)))
        mapa.add(Casilla(32, TipoCasilla.ESCALERA, 47, findViewById<TextView>(R.id.casilla32)))
        mapa.add(Casilla(33, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla33)))
        mapa.add(Casilla(34, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla34)))
        mapa.add(Casilla(35, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla35)))
        mapa.add(Casilla(36, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla36)))
        mapa.add(Casilla(37, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla37)))
        mapa.add(Casilla(38, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla38)))
        mapa.add(Casilla(39, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla39)))
        mapa.add(Casilla(40, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla40)))
        mapa.add(Casilla(41, TipoCasilla.SERPIENTE, 22, findViewById<TextView>(R.id.casilla41)))
        mapa.add(Casilla(42, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla42)))
        mapa.add(Casilla(43, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla43)))
        mapa.add(Casilla(44, TipoCasilla.ESCALERA, 61, findViewById<TextView>(R.id.casilla44)))
        mapa.add(Casilla(45, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla45)))
        mapa.add(Casilla(46, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla46)))
        mapa.add(Casilla(47, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla47)))
        mapa.add(Casilla(48, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla48)))
        mapa.add(Casilla(49, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla49)))
        mapa.add(Casilla(50, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla50)))
        mapa.add(Casilla(51, TipoCasilla.SERPIENTE, 5, findViewById<TextView>(R.id.casilla51)))
        mapa.add(Casilla(52, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla52)))
        mapa.add(Casilla(53, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla53)))
        mapa.add(Casilla(54, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla54)))
        mapa.add(Casilla(55, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla55)))
        mapa.add(Casilla(56, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla56)))
        mapa.add(Casilla(57, TipoCasilla.SERPIENTE, 38, findViewById<TextView>(R.id.casilla57)))
        mapa.add(Casilla(58, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla58)))
        mapa.add(Casilla(59, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla59)))
        mapa.add(Casilla(60, TipoCasilla.SERPIENTE, 43, findViewById<TextView>(R.id.casilla60)))
        mapa.add(Casilla(61, TipoCasilla.NORMAL, 0, findViewById<TextView>(R.id.casilla61)))
        mapa.add(Casilla(62, TipoCasilla.SERPIENTE, 48, findViewById<TextView>(R.id.casilla62)))
        mapa.add(Casilla(63, TipoCasilla.SERPIENTE, 50, findViewById<TextView>(R.id.casilla63)))
        mapa.add(Casilla(64, TipoCasilla.GANADOR, 0, findViewById<TextView>(R.id.casilla64)))

        val btn: ImageButton = findViewById(R.id.btnReturn)
        btn.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivityForResult(intent, 1)
        }

        val btn2: Button = findViewById(R.id.botonDado)
        btn2.setOnClickListener {

            // Se tiran los dado
            val Dado = Random
            val Dado2 = Random
            val Resultado = Dado.nextInt(6) + 1
            val Resultado2 = Dado2.nextInt(6) + 1

            var posAnterior = 0

            //Se pregunta por quien tiene el turno
            if (jugador.turno) {

                // Se muestran los dados
                TV1.text = Resultado.toString()
                TV2.text = Resultado2.toString()

                //Saco 3 veces seis por lo tanto se le devuelve a la posicion inicial
                if (jugador.contDado == 3) {
                    val duracionToast =
                        Toast.LENGTH_LONG // Puedes usar Toast.LENGTH_SHORT o Toast.LENGTH_LONG
                    val toast = Toast.makeText(
                        this,
                        "Lo siento, ya sacaste tres veces seis con ambos dados!!",
                        duracionToast
                    )
                    toast.show()

                    for (j in 0 until mapa.size) {

                        if (mapa[j].Numero == jugador.Posicion) {
                            if (mapa[j].lugar.text.toString() == "" + jugador.Posicion + ".XO") {
                                mapa[j].lugar.text = "" + jugador.Posicion.toString() + ". O"
                            } else if (mapa[j].lugar.text.toString() == "" + jugador.Posicion + ".X ") {
                                mapa[j].lugar.text ="" + jugador.Posicion.toString() + ".  "
                            }
                        }/*/* AQUI QUEDE*/*/

                        jugador.Posicion = 1
                        if (mapa[j].lugar.text.toString() == "" + jugador.Posicion + ". O") {
                            mapa[j].lugar.text ="" + jugador.Posicion + ".XO"
                            val textoParaLeer = "${jugador.Posicion}"
                            mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                            //CAMBIO DE POSICION
                        } else if (mapa[j].lugar.text.toString() == "" + jugador.Posicion + ".  ") {
                            mapa[j].lugar.text ="" + jugador.Posicion + ".X "
                            val textoParaLeer = "${jugador.Posicion}"
                            mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                            //CAMBIO DE POSICION
                        }

                    }

                } else {

                    // cambio de posicion
                    posAnterior = jugador.Posicion
                    jugador.Posicion = jugador.Posicion + Resultado + Resultado2

                    if (jugador.Posicion > 64) {

                        val duracionToast =
                            Toast.LENGTH_LONG // Puedes usar Toast.LENGTH_SHORT o Toast.LENGTH_LONG
                        val toast = Toast.makeText(
                            this,
                            "Lo siento, no fue suficiente para llegar a la meta!!",
                            duracionToast
                        )
                        toast.show()
                        jugador.Posicion = posAnterior
                    } else {

                        // Revision de cambios de posicion en tablero
                        for (j in 0 until mapa.size) {

                            //Se borra la posicion anterior del jugador
                            if (mapa[j].Numero == posAnterior) {
                                if (mapa[j].lugar.text.toString() == "" + posAnterior + ".XO") {
                                    mapa[j].lugar.setText("" + posAnterior + ". O")
                                } else {
                                    mapa[j].lugar.setText("" + posAnterior + ".  ")
                                }
                            }

                            if (mapa[j].Numero == jugador.Posicion) {
                                // Se revisa si la nueva posicion es una escalera
                                if (mapa[j].Tipo == TipoCasilla.ESCALERA) {
//ESCALERAAAAAAAAAAAAAAAAAAAAAAAAAAAA
                                    posAnterior = jugador.Posicion
                                    //Se borra la posicion anterior del jugador
                                    if (mapa[j].Numero == posAnterior) {
                                        if (mapa[j].lugar.text.toString() == "" + posAnterior + ".XO") {
                                            mapa[j].lugar.setText("" + posAnterior + ". O")
                                        } else {
                                            mapa[j].lugar.setText("" + posAnterior + ".  ")
                                        }
                                    }

                                    jugador.Posicion = mapa[j].destino

                                    if (mapa[j].lugar.text.toString() == "" + jugador.Posicion + ". O") {
                                        mapa[j].lugar.setText("" + jugador.Posicion + ".XO")
                                        val textoParaLeer = "${jugador.Posicion}"
                                        mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                                        //CAMBIO DE POSICION
                                    } else if (mapa[j].lugar.text.toString() == "" + jugador.Posicion + ".  "){
                                        mapa[j].lugar.setText("" + jugador.Posicion + ".X ")
                                        val textoParaLeer = "${jugador.Posicion}"
                                        mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                                        //CAMBIO DE POSICION
                                    }
                                    //Se revisa si es serpiente
                                } else if (mapa[j].Tipo == TipoCasilla.SERPIENTE) {

                                    posAnterior = jugador.Posicion
                                    //Se borra la posicion anterior del jugador
                                    if (mapa[j].Numero == posAnterior) {
                                        if (mapa[j].lugar.text.toString() == "" + posAnterior + ".XO") {
                                            mapa[j].lugar.setText("" + posAnterior + ". O")
                                        } else {
                                            mapa[j].lugar.setText("" + posAnterior + ".  ")
                                        }
                                    }

                                    jugador.Posicion = mapa[j].destino

                                    if (mapa[j].lugar.text.toString() == "" + jugador.Posicion + ". O") {
                                        mapa[j].lugar.setText("" + jugador.Posicion + ".XO")
                                        val textoParaLeer = "${jugador.Posicion}"
                                        mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                                        //CAMBIO DE POSICION
                                    } else if (mapa[j].lugar.text.toString() == "" + jugador.Posicion + ".  ") {
                                        mapa[j].lugar.setText("" + jugador.Posicion + ".X ")
                                        val textoParaLeer = "${jugador.Posicion}"
                                        mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                                        //CAMBIO DE POSICION
                                    }
                                    // Se revisa si es normal
                                } else if (mapa[j].Tipo == TipoCasilla.NORMAL) {

                                    if (mapa[j].lugar.text.toString() == "" + jugador.Posicion + ". O") {
                                        mapa[j].lugar.setText("" + jugador.Posicion + ".XO")
                                        val textoParaLeer = "${jugador.Posicion}"
                                        mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                                        //CAMBIO DE POSICION
                                    } else {
                                        mapa[j].lugar.setText("" + jugador.Posicion + ".X ")
                                        val textoParaLeer = "${jugador.Posicion}"
                                        mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                                        //CAMBIO DE POSICION
                                    }

                                } else if (mapa[j].Tipo == TipoCasilla.GANADOR) {

                                    if (mapa[j].lugar.text.toString() == "" + jugador.Posicion + ". O") {
                                        mapa[j].lugar.setText("" + jugador.Posicion + ".XO")
                                        val textoParaLeer = "${jugador.Posicion} Congratulations, you win!"
                                        mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                                        //CAMBIO DE POSICION CASILLA GANADORA
                                    } else {
                                        mapa[j].lugar.setText("" + jugador.Posicion + ".X ")
                                        val textoParaLeer = "${jugador.Posicion} Congratulations, you win!"
                                        mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                                        //CAMBIO DE POSICION CASILLA GANADORA
                                    }

                                    textoGanador.setText(textoGanador.text.toString() + "\n" + jugador.nombre)
                                    jugador.turno = false
                                    jugador2.turno = false
                                }
                            }
                        }
                    }

                }


                // cambio de turno
                var suma = Resultado + Resultado2
                if (suma == 6) {
                    jugador.turno = true
                    jugador2.turno = false
                    txtJname.setText("Turno: " + jugador.nombre.toUpperCase())

                    val duracionToast =
                        Toast.LENGTH_LONG // Puedes usar Toast.LENGTH_SHORT o Toast.LENGTH_LONG
                    val toast = Toast.makeText(
                        this,
                        "Vamos tus dados suman 6, Tienes otro intento!!",
                        duracionToast
                    )
                    toast.show()

                    jugador.contDado += 1
                } else {
                    jugador.turno = false
                    jugador2.turno = true
                    txtJname.setText("Turno: " + jugador2.nombre.toUpperCase())

                    jugador.contDado = 0
                }

            } else {
                if(jugador2.turno){

                    // Se muestran los dados
                    TV1.text = Resultado.toString()
                    TV2.text = Resultado2.toString()

                    //Saco 3 veces seis por lo tanto se le devuelve a la posicion inicial
                    if (jugador2.contDado == 3) {
                        val duracionToast =
                            Toast.LENGTH_LONG // Puedes usar Toast.LENGTH_SHORT o Toast.LENGTH_LONG
                        val toast = Toast.makeText(
                            this,
                            "Lo siento, ya sacaste tres veces seis con ambos dados!!",
                            duracionToast
                        )
                        toast.show()

                        for (j in 0 until mapa.size) {

                            if (mapa[j].Numero == jugador2.Posicion) {
                                if (mapa[j].lugar.text.toString() == "" + jugador2.Posicion + ".XO") {
                                    mapa[j].lugar.text = "" + jugador2.Posicion.toString() + ".X "
                                } else if (mapa[j].lugar.text.toString() == "" + jugador2.Posicion + ". O") {
                                    mapa[j].lugar.text ="" + jugador2.Posicion.toString() + ".  "
                                }
                            }

                            jugador2.Posicion = 1
                            if (mapa[j].lugar.text.toString() == "" + jugador2.Posicion + ".X ") {
                                mapa[j].lugar.text ="" + jugador2.Posicion + ".XO"
                                val textoParaLeer = "${jugador2.Posicion}"
                                mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                                //CAMBIO DE POSICION
                            } else if(mapa[j].lugar.text.toString() == "" + jugador2.Posicion + ".  "){
                                mapa[j].lugar.text ="" + jugador2.Posicion + ". O"
                                val textoParaLeer = "${jugador2.Posicion}"
                                mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                                //CAMBIO DE POSICION
                            }

                        }

                    } else {

                        // cambio de posicion
                        posAnterior = jugador2.Posicion
                        jugador2.Posicion = jugador2.Posicion + Resultado + Resultado2

                        if (jugador2.Posicion > 64) {

                            val duracionToast =
                                Toast.LENGTH_LONG // Puedes usar Toast.LENGTH_SHORT o Toast.LENGTH_LONG
                            val toast = Toast.makeText(
                                this,
                                "Lo siento, no fue suficiente para llegar a la meta!!",
                                duracionToast
                            )
                            toast.show()
                            jugador2.Posicion = posAnterior

                        } else {

                            // Revision de cambios de posicion en tablero
                            for (j in 0 until mapa.size) {

                                //Se borra la posicion anterior del jugador
                                if (mapa[j].Numero == posAnterior) {
                                    if (mapa[j].lugar.text.toString() == "" + posAnterior + ".XO") {
                                        mapa[j].lugar.setText("" + posAnterior + ".X ")
                                    } else {
                                        mapa[j].lugar.setText("" + posAnterior + ".  ")
                                    }
                                }

                                if (mapa[j].Numero == jugador2.Posicion) {
                                    // Se revisa si la nueva posicion es una escalera
                                    if (mapa[j].Tipo == TipoCasilla.ESCALERA) {
//ESCALERAAAAAAAAAAAAAAAAAAAAAAAAAAAA
                                        posAnterior = jugador2.Posicion
                                        //Se borra la posicion anterior del jugador
                                        if (mapa[j].Numero == posAnterior) {
                                            if (mapa[j].lugar.text.toString() == "" + posAnterior + ".XO") {
                                                mapa[j].lugar.setText("" + posAnterior + ".X ")
                                            } else {
                                                mapa[j].lugar.setText("" + posAnterior + ".  ")
                                            }
                                        }

                                        jugador2.Posicion = mapa[j].destino

                                        if (mapa[j].lugar.text.toString() == "" + jugador2.Posicion + ".X ") {
                                            mapa[j].lugar.setText("" + jugador2.Posicion + ".XO")
                                            val textoParaLeer = "${jugador2.Posicion}"
                                            mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                                            //CAMBIO DE POSICION
                                        } else if (mapa[j].lugar.text.toString() == "" + jugador2.Posicion + ".  "){
                                            mapa[j].lugar.setText("" + jugador2.Posicion + ". O")
                                            val textoParaLeer = "${jugador2.Posicion}"
                                            mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                                            //CAMBIO DE POSICION
                                        }
                                        //Se revisa si es serpiente
                                    } else if (mapa[j].Tipo == TipoCasilla.SERPIENTE) {

                                        posAnterior = jugador2.Posicion
                                        //Se borra la posicion anterior del jugador
                                        if (mapa[j].Numero == posAnterior) {
                                            if (mapa[j].lugar.text.toString() == "" + posAnterior + ".XO") {
                                                mapa[j].lugar.setText("" + posAnterior + ".X ")
                                            } else {
                                                mapa[j].lugar.setText("" + posAnterior + ".  ")
                                            }
                                        }

                                        jugador2.Posicion = mapa[j].destino

                                        if (mapa[j].lugar.text.toString() == "" + jugador2.Posicion + ".X ") {
                                            mapa[j].lugar.setText("" + jugador2.Posicion + ".XO")
                                            val textoParaLeer = "${jugador2.Posicion}"
                                            mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                                            //CAMBIO DE POSICION
                                        } else if (mapa[j].lugar.text.toString() == "" + jugador2.Posicion + ".  ") {
                                            mapa[j].lugar.setText("" + jugador2.Posicion + ". O")
                                            val textoParaLeer = "${jugador2.Posicion}"
                                            mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                                            //CAMBIO DE POSICION
                                        }
                                        // Se revisa si es normal
                                    } else if (mapa[j].Tipo == TipoCasilla.NORMAL) {

                                        if (mapa[j].lugar.text.toString() == "" + jugador2.Posicion + ".X ") {
                                            mapa[j].lugar.setText("" + jugador2.Posicion + ".XO")
                                            val textoParaLeer = "${jugador2.Posicion}"
                                            mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                                            //CAMBIO DE POSICION
                                        } else {
                                            mapa[j].lugar.setText("" + jugador2.Posicion + ". O")
                                            val textoParaLeer = "${jugador2.Posicion}"
                                            mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                                            //CAMBIO DE POSICION
                                        }

                                    } else if (mapa[j].Tipo == TipoCasilla.GANADOR) {

                                        if (mapa[j].lugar.text.toString() == "" + jugador.Posicion + ".X ") {
                                            mapa[j].lugar.setText("" + jugador.Posicion + ".XO")
                                            val textoParaLeer = "${jugador2.Posicion} Congratulations, you win!"
                                            mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                                            //CAMBIO DE POSICION
                                        } else {
                                            mapa[j].lugar.setText("" + jugador.Posicion + ". O")
                                            val textoParaLeer = "${jugador2.Posicion} Congratulations, you win!"
                                            mTTS.speak(textoParaLeer, TextToSpeech.QUEUE_FLUSH, null, null)
                                            //CAMBIO DE POSICION
                                        }
                                        textoGanador.setText(textoGanador.text.toString() + "\n" + jugador2.nombre)
                                        jugador.turno = false
                                        jugador2.turno = false
                                    }
                                }
                            }
                        }

                    }


                    // cambio de turno
                    var suma = Resultado + Resultado2
                    if (suma == 6) {
                        jugador.turno = false
                        jugador2.turno = true
                        txtJname.setText("Turno: " + jugador2.nombre.toUpperCase())

                        val duracionToast =
                            Toast.LENGTH_LONG // Puedes usar Toast.LENGTH_SHORT o Toast.LENGTH_LONG
                        val toast = Toast.makeText(
                            this,
                            "Vamos tus dados suman 6, Tienes otro intento!!",
                            duracionToast
                        )
                        toast.show()

                        jugador2.contDado += 1
                    } else {
                        jugador.turno = true
                        jugador2.turno = false
                        txtJname.setText("Turno: " + jugador.nombre.toUpperCase())

                        jugador2.contDado = 0
                    }

                }
            }
        }
    }
}
