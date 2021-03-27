package com.example.horario

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var bloqueado:Boolean = true
    private var inferior:Int = 0
    private var superior:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE
        setContentView(R.layout.activity_main)

        home.setOnClickListener {
            val intent: Intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }
        bloquear.setOnClickListener {
            if (bloqueado){
                bloquear.setImageResource(R.drawable.ic_candado_01)
                bloqueado = false
            }else{
                bloquear.setImageResource(R.drawable.ic_candado_02)
                bloqueado = true
            }
        }

        //row_11.visibility = View.GONE
        //row_12.visibility = View.GONE

        c1.setOnClickListener {showDetalles(c1)}
        c2.setOnClickListener {showDetalles(c2)}
        c3.setOnClickListener {showDetalles(c3)}
        c4.setOnClickListener {showDetalles(c4)}
        c5.setOnClickListener {showDetalles(c5)}
        c6.setOnClickListener {showDetalles(c6)}
        c7.setOnClickListener {showDetalles(c7)}
        c8.setOnClickListener {showDetalles(c8)}
        c9.setOnClickListener {showDetalles(c9)}
        c10.setOnClickListener {showDetalles(c10)}
        c11.setOnClickListener {showDetalles(c11)}
        c12.setOnClickListener {showDetalles(c12)}
        c13.setOnClickListener {showDetalles(c13)}
        c14.setOnClickListener {showDetalles(c14)}
        c15.setOnClickListener {showDetalles(c15)}
        c16.setOnClickListener {showDetalles(c16)}
        c17.setOnClickListener {showDetalles(c17)}
        c18.setOnClickListener {showDetalles(c18)}
        c19.setOnClickListener {showDetalles(c19)}
        c20.setOnClickListener {showDetalles(c20)}
        c21.setOnClickListener {showDetalles(c21)}
        c22.setOnClickListener {showDetalles(c22)}
        c23.setOnClickListener {showDetalles(c23)}
        c24.setOnClickListener {showDetalles(c24)}
        c25.setOnClickListener {showDetalles(c25)}
        c26.setOnClickListener {showDetalles(c26)}
        c27.setOnClickListener {showDetalles(c27)}
        c28.setOnClickListener {showDetalles(c28)}
        c29.setOnClickListener {showDetalles(c29)}
        c30.setOnClickListener {showDetalles(c30)}
        c31.setOnClickListener {showDetalles(c31)}
        c32.setOnClickListener {showDetalles(c32)}
        c33.setOnClickListener {showDetalles(c33)}
        c34.setOnClickListener {showDetalles(c34)}
        c35.setOnClickListener {showDetalles(c35)}
        c36.setOnClickListener {showDetalles(c36)}
        c37.setOnClickListener {showDetalles(c37)}
        c38.setOnClickListener {showDetalles(c38)}
        c39.setOnClickListener {showDetalles(c39)}
        c40.setOnClickListener {showDetalles(c40)}
        c41.setOnClickListener {showDetalles(c41)}
        c42.setOnClickListener {showDetalles(c42)}
        c43.setOnClickListener {showDetalles(c43)}
        c44.setOnClickListener {showDetalles(c44)}
        c45.setOnClickListener {showDetalles(c45)}
        c46.setOnClickListener {showDetalles(c46)}
        c47.setOnClickListener {showDetalles(c47)}
        c48.setOnClickListener {showDetalles(c48)}
        c49.setOnClickListener {showDetalles(c49)}
        c50.setOnClickListener {showDetalles(c50)}
        c51.setOnClickListener {showDetalles(c51)}
        c52.setOnClickListener {showDetalles(c52)}
        c53.setOnClickListener {showDetalles(c53)}
        c54.setOnClickListener {showDetalles(c54)}
        c55.setOnClickListener {showDetalles(c55)}
        c56.setOnClickListener {showDetalles(c56)}
        c57.setOnClickListener {showDetalles(c57)}
        c58.setOnClickListener {showDetalles(c58)}
        c59.setOnClickListener {showDetalles(c59)}
        c60.setOnClickListener {showDetalles(c60)}

    }


    private fun showDetalles(caja:TextView){
        if (bloqueado){
            Toast.makeText(this, "esta bloquedao", Toast.LENGTH_SHORT).show()
        }else{
            caja.setText(R.string.nada)
        }
    }


    override fun onBackPressed() {
        //super.onBackPressed()
    }
}
