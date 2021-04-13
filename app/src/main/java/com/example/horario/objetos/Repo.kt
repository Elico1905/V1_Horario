package com.example.horario.objetos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore

class Repo{
/*
    fun getUserData ():LiveData<MutableList<MensajesObj>>{
        val mutableData = MutableLiveData<MutableList<MensajesObj>>()
        FirebaseFirestore.getInstance().collection("solicitudes").get().addOnSuccessListener { result ->
            var listData = mutableListOf<MensajesObj>()
            for(document in result){
                listData.add(MensajesObj(document.getString("titulo").toString(),
                        document.getString("autor").toString(),
                        document.getString("editorial").toString(),
                        document.getString("comentario").toString(),
                        document.getString("estado").toString(),
                        document.getString("fecha").toString(),
                        document.getString("respuesta").toString()))
            }
            mutableData.value = listData
        }
        return mutableData
    }

 */


    fun getUserData (CONDITION:String,CAMPO:String):LiveData<MutableList<grupoObj>>{
        val mutableData = MutableLiveData<MutableList<grupoObj>>()
        FirebaseFirestore.getInstance().collection("grupos").whereEqualTo("${CAMPO}","${CONDITION}").get().addOnSuccessListener { result ->
            var listData = mutableListOf<grupoObj>()
            for(document in result){
                listData.add(grupoObj(document.getString("grupo").toString(),5,8))
            }
            mutableData.value = listData
        }
        return mutableData
    }
}