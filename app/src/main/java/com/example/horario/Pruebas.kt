package com.example.horario

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_pruebas.*

class Pruebas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pruebas)

        t1.setOnClickListener {
            show(t1,"t1")
        }
        t2.setOnClickListener {
            show(t2,"t2")
        }
        t3.setOnClickListener {
            show(t3,"t3")
        }
        t4.setOnClickListener {
            show(t4,"t4")
        }
    }

    private fun show(caja:TextView,id:String){
        if (id != "t4"){
            caja.text = "${id}"
        }else{
            t1.text = "texto 01"
            t2.text = "texto 02"
            t3.text = "texto 03"
        }

    }
}