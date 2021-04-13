package com.example.horario.objetos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    private val repo = Repo()

    fun fetchUserData(DATO:String,CAMPO:String):LiveData<MutableList<grupoObj>>{
        val mutableData = MutableLiveData<MutableList<grupoObj>>()
        repo.getUserData(DATO,"${CAMPO}").observeForever{ userList ->
            mutableData.value = userList
        }

        return mutableData
    }
}