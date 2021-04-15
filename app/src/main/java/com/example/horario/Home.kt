package com.example.horario

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.horario.objetos.MainAdapterAdmin
import com.example.horario.objetos.grupoObj
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.registro_nombre
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.horario.objetos.MainViewModel

class Home : AppCompatActivity() {

    private val bd = FirebaseFirestore.getInstance()

    private lateinit var adapter: MainAdapterAdmin

    private val viewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    private var lista = mutableListOf<grupoObj>();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_home)

        pintar()

        adapter = MainAdapterAdmin(this)
        home_recycler.layoutManager = LinearLayoutManager(this)
        home_recycler.adapter = adapter
        observeData()



        home_title.text = "User: ${getName()}"


        regresar.setOnClickListener {
            mostrarSalir()
        }

        home_btn_salir.setOnClickListener {
            salir()
        }
        home_btn_cancelar.setOnClickListener {
            ocultarSalir()
        }
        home_add.setOnClickListener {
            mostrarAgregar()
        }
        home_btn_cancelar_02.setOnClickListener {
            ocultarAgregar()
        }
        home_btn_registrar.setOnClickListener {
            registrar()
        }
    }

    private fun salir() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()

        val intent: Intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }

    private fun mostrarSalir() {
        pintar()
        home_fondo.visibility = View.VISIBLE
        home_salir.visibility = View.VISIBLE
    }

    private fun ocultarSalir() {
        pintarRegreso()
        home_fondo.visibility = View.GONE
        home_salir.visibility = View.GONE
    }

    private fun mostrarAgregar() {
        pintar()
        home_fondo.visibility = View.VISIBLE
        home_agregar.visibility = View.VISIBLE
    }

    private fun ocultarAgregar() {
        pintarRegreso()
        clearMensaje()
        home_fondo.visibility = View.GONE
        home_agregar.visibility = View.GONE
    }

    private fun getCorreo(): String {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        var correo = prefs.getString("correo", "2021ff").toString()
        return correo
    }

    private fun getName(): String {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        var nombre = prefs.getString("nombre", "2021ff").toString()


        var aux: String = ""

        var i: Int = 1
        while (i < nombre.length) {
            aux += nombre[i]
            i++
        }

        return "${nombre.get(0).toUpperCase()}${aux.toLowerCase()}"
    }

    private fun registrar() {
        if (registro_nombre.text.isNotEmpty()) {
            if (!validarGrupos(registro_nombre.text.toString().toUpperCase())) {
                bd.collection("grupos").document("${getCorreo()}_${registro_nombre.text.toString()}").set(
                        hashMapOf("grupo" to registro_nombre.text.toString().toUpperCase(),
                                "correo" to getCorreo()))


                message("Grupo agregado", 2)
                clearMensaje()
                observeData()
                ocultarAgregar()
            } else {
                message("Ya existe un grupo registrado con ese nombre", 1)
            }

        } else {
            message("campo vacio", 1)
        }
    }

    private fun validarGrupos(cadena: String): Boolean {
        var resul = false
        var tam = 0
        while (tam < lista.size) {
            if (cadena.equals(lista[tam].nombre)) {
                resul = true
                break
            }
            tam++
        }
        return resul
    }

    override fun onBackPressed() {
        //super.onBackPressed()
        if (home_fondo.visibility == View.VISIBLE) {
            if (home_salir.visibility == View.VISIBLE) {
                ocultarSalir()
            }
            if (home_agregar.visibility == View.VISIBLE) {
                ocultarAgregar()
            }
        } else {
            mostrarSalir()
        }
    }

    private fun message(cadena: String, possition: Int) {
        if (possition == 1) {
            Toast.makeText(this, "${cadena}", Toast.LENGTH_SHORT).show()
        }
        if (possition == 2) {
            Toast.makeText(this, "${cadena}", Toast.LENGTH_LONG).show()
        }
    }

    private fun clearMensaje() {
        registro_nombre.setText("")
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }


    private fun observeData() {
        viewModel.fetchUserData("${getCorreo()}", "correo").observe(this, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
            if (adapter.itemCount > 0) {
                home_mensaje.visibility = View.GONE
            } else {
                home_mensaje.visibility = View.VISIBLE
            }
            lista = adapter.returnListData()
            home_cargando.visibility = View.GONE

            pintarRegreso()
        })

    }

    private fun pintar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.barra01)
    }

    private fun pintarRegreso() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.colorPrimaryVariant)
    }

    private fun eliminar(){
        bd.collection("users")
                .document("elico1904@gmail.com").delete()
    }
}

