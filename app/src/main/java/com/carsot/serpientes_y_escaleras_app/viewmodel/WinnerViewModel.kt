package com.carsot.serpientes_y_escaleras_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

data class Winner(
    val User: String = "",
    val Date: String = ""
)

class WinnerViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _winners = MutableLiveData<List<Winner>>()
    val winners: LiveData<List<Winner>> get() = _winners

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        fetchWinners()
    }

    fun fetchWinners() {
        db.collection("winners")
            .get()
            .addOnSuccessListener { documents ->
                val winnerList = documents.mapNotNull { it.toObject<Winner>() }
                _winners.value = winnerList
            }
            .addOnFailureListener { e ->
                _error.value = "Error fetching winners: ${e.message}"
            }
    }

    fun deleteWinner(userName: String) {
        db.collection("winners").document(userName)
            .delete()
            .addOnSuccessListener {
                fetchWinners() // Refrescar lista tras la eliminación
            }
            .addOnFailureListener { e ->
                _error.value = "Error deleting winner: ${e.message}"
            }
    }
}
