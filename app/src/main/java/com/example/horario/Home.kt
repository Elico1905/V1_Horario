package com.example.horario

import android.content.Context
import android.content.Intent
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
        setContentView(R.layout.activity_home)


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
        home_btn_registrar.setOnClickListener{
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
        home_fondo.visibility = View.VISIBLE
        home_salir.visibility = View.VISIBLE
    }

    private fun ocultarSalir() {
        home_fondo.visibility = View.GONE
        home_salir.visibility = View.GONE
    }

    private fun mostrarAgregar(){
        home_fondo.visibility = View.VISIBLE
        home_agregar.visibility = View.VISIBLE
    }

    private fun ocultarAgregar(){
        home_fondo.visibility = View.GONE
        home_agregar.visibility = View.GONE
    }

    private fun getCorreo():String{
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        var correo = prefs.getString("correo", "2021ff").toString()
        return correo
    }
    private fun getName():String{
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        var nombre = prefs.getString("nombre", "2021ff").toString()



        var aux:String = ""

        var i:Int = 1
        while (i < nombre.length) {
            aux += nombre[i]
            i++
        }

        return "${nombre.get(0).toUpperCase()}${aux.toLowerCase()}"
    }
    private fun registrar(){
        if (registro_nombre.text.isNotEmpty()){
            bd.collection("grupos").document("${getCorreo()}_${registro_nombre.text.toString()}").set(
                    hashMapOf("grupo" to registro_nombre.text.toString(),
                              "correo" to getCorreo()))

            registro_nombre.setText("")
            message("Grupo agregado",2)

            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

            ocultarAgregar()
        }else{
            message("campo vacio",1)
        }
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
        }else{
            mostrarSalir()
        }
    }
    private fun message(cadena:String,possition:Int){
        if (possition == 1){
            Toast.makeText(this, "${cadena}", Toast.LENGTH_SHORT).show()
        }
        if (possition == 2){
            Toast.makeText(this, "${cadena}", Toast.LENGTH_LONG).show()
        }
    }

    fun observeData(){
        viewModel.fetchUserData("${getCorreo()}","correo").observe(this, Observer {
            adapter.setListData(it)
            adapter.notifyDataSetChanged()
            if (adapter.itemCount > 0) {
                home_recycler.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "no hay datos", Toast.LENGTH_SHORT).show()
            }
            lista = adapter.returnListData()
        })

    }


}

