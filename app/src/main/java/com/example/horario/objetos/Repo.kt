package com.example.horario.objetos

import android.content.Context
import android.preference.PreferenceManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.horario.R
import com.google.firebase.firestore.FirebaseFirestore

class Repo {
    private val bd = FirebaseFirestore.getInstance()

    fun getUserData(CONDITION: String, CAMPO: String): LiveData<MutableList<grupoObj>> {

        val mutableData = MutableLiveData<MutableList<grupoObj>>()

        FirebaseFirestore.getInstance().collection("grupos")
                .whereEqualTo("${CAMPO}", "${CONDITION}")
                .get().addOnSuccessListener { result ->
                    var listData = mutableListOf<grupoObj>()
                    for (document in result) {
                        listData.add(grupoObj(document.getString("grupo").toString(), 0, 0,"${CONDITION}"))
                    }
                    mutableData.value = listData
                }
        return mutableData
    }
}