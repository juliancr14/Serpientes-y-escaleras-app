package com.carsot.serpientes_y_escaleras_app.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.carsot.serpientes_y_escaleras_app.R
import com.carsot.serpientes_y_escaleras_app.viewmodel.Winner
import com.carsot.serpientes_y_escaleras_app.viewmodel.WinnerViewModel

class WinnerActivity : AppCompatActivity() {

    private val viewModel: WinnerViewModel by viewModels()
    private lateinit var winnerAdapter: WinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_winner)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewWinners)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.winners.observe(this, Observer { winners ->
            winnerAdapter = WinnerAdapter(winners) { userName ->
                viewModel.deleteWinner(userName)
            }
            recyclerView.adapter = winnerAdapter
        })

        viewModel.error.observe(this, Observer { error ->
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
