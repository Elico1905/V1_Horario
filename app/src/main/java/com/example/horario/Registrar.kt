package com.example.horario

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
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
            val intent: Intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        registro_registrar.setOnClickListener {
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
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}