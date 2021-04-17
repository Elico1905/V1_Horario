package com.example.horario

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_registrar.*

class Registrar : AppCompatActivity() {

    private val bd = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        val objintent: Intent = intent
        var correo: String? = objintent.getStringExtra("correo")
        var nombre: String? = objintent.getStringExtra("nombre")
        var apellidos: String? = objintent.getStringExtra("apellidos")

        registro_correo.setText(correo)
        registro_nombre.setText(nombre)
        registro_apellidos.setText(apellidos)

        registro_cancelar.setOnClickListener {
            pintar()
            registro_salir.visibility = View.VISIBLE
        }
        registro_btn_si.setOnClickListener {
            val intent: Intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()

        }
        registro_btn_no.setOnClickListener {
            registro_salir.visibility = View.GONE
            despintar()
        }

        registro_btn_cancelar.setOnClickListener {
            despintar()
            registro_registro.visibility = View.GONE
        }
        registro_btn_registrar.setOnClickListener {
            if (!nombre.isNullOrEmpty() || !apellidos.isNullOrEmpty()) {
                bd.collection("users").document(registro_correo.text.toString()).set(
                    hashMapOf("nombre" to registro_nombre.text.toString(),
                        "apellidos" to registro_apellidos.text.toString(),
                        "correo" to registro_correo.text.toString()))

                val prefs =getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
                prefs.putString("correo",registro_correo.text.toString())
                prefs.putString("nombre",registro_nombre.text.toString())
                prefs.putString("apellidos",registro_apellidos.text.toString())
                prefs.apply()

                val intent: Intent = Intent(this, Home::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "llena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
        registro_registrar.setOnClickListener {
            pintar()
            registro_registro.visibility = View.VISIBLE
        }
    }
    private fun pintar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.barra01)
    }

    private fun despintar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor =
            ContextCompat.getColor(applicationContext, R.color.colorPrimaryVariant)
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }
}