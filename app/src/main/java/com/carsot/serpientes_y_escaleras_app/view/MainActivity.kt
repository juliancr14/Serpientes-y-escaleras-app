package com.carsot.serpientes_y_escaleras_app.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.carsot.serpientes_y_escaleras_app.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var txtName1 = findViewById<TextView>(R.id.etxtPplayer)
        var txtName2 = findViewById<TextView>(R.id.etxtSplayer)

        val btn: Button = findViewById(R.id.btnStart)
        btn.setOnClickListener {
            val name1 = txtName1.text.toString()
            val name2 = txtName2.text.toString()


            val intent: Intent = Intent(this, BoardActivity::class.java)
            intent.putExtra("Nombre1",name1)
            intent.putExtra("Nombre2",name2)
            startActivity(intent)
        }
    }


}
