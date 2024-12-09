package com.carsot.serpientes_y_escaleras_app.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.carsot.serpientes_y_escaleras_app.R
import com.carsot.serpientes_y_escaleras_app.viewmodel.Winner

class WinnerAdapter(
    private val winners: List<Winner>,
    private val onDelete: (String) -> Unit
) : RecyclerView.Adapter<WinnerAdapter.WinnerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WinnerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_winner, parent, false)
        return WinnerViewHolder(view)
    }

    override fun onBindViewHolder(holder: WinnerViewHolder, position: Int) {
        val winner = winners[position]
        holder.bind(winner, onDelete)
    }

    override fun getItemCount(): Int = winners.size

    class WinnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName: TextView = itemView.findViewById(R.id.tvUserName)
        private val date: TextView = itemView.findViewById(R.id.tvDate)
        private val deleteButton: Button = itemView.findViewById(R.id.btnDelete)

        fun bind(winner: Winner, onDelete: (String) -> Unit) {
            userName.text = winner.User
            date.text = winner.Date
            deleteButton.setOnClickListener { onDelete(winner.User) }
        }
    }
}
