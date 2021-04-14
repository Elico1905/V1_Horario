package com.example.horario

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.example.horario.objetos.GETDATA
import com.example.horario.objetos.Repo
import com.example.horario.objetos.grupoObj
import com.example.horario.objetos.materiaObj
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.text.ParsePosition
import com.example.horario.R.drawable.back_azul as back_azul1

class MainActivity : AppCompatActivity() {

    private var bloqueado: Boolean = true
    private var inferior: Int = 0
    private var superior: Int = 0
    private var GRUPO: String = ""
    private var color: Int = 1

    private val bd = FirebaseFirestore.getInstance()
    private lateinit var cajaTemp: TextView

    private var materias = mutableListOf<materiaObj>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_main)


        val objintent: Intent = intent
        var grupo: String? = objintent.getStringExtra("grupo")
        GRUPO = grupo.toString()
        main_grupo.text = grupo

        cargarMaterias(getCorreo(), GRUPO)

        val prefs = getSharedPreferences(getString(R.string.prefs_file_data_user), Context.MODE_PRIVATE).edit()
        prefs.putBoolean("estado", true)
        prefs.putString("grupo", grupo)
        prefs.apply()


        main_fondo01.setOnClickListener {
            selection(main_fondo01, main_materia01)
        }
        main_fondo02.setOnClickListener {
            selection(main_fondo02, main_materia02)
        }
        main_fondo03.setOnClickListener {
            selection(main_fondo03, main_materia03)
        }
        main_fondo04.setOnClickListener {
            selection(main_fondo04, main_materia04)
        }
        main_fondo05.setOnClickListener {
            selection(main_fondo05, main_materia05)
        }
        main_fondo06.setOnClickListener {
            selection(main_fondo06, main_materia06)
        }
        main_fondo07.setOnClickListener {
            selection(main_fondo07, main_materia07)
        }

        main_agregar.setOnClickListener {
            if (main_nombre.text.isNotEmpty()) {
                if (main_fondo06.visibility == View.GONE){
                    bd.collection("materias").document("${getCorreo()}_${GRUPO}_${main_nombre.text.toString()}").set(
                            hashMapOf(
                                    "materia" to main_nombre.text.toString(),
                                    "correo" to getCorreo(),
                                    "grupo" to GRUPO,
                                    "color" to "${color}"
                            ))
                    limpiar()
                    ocultarmaterias();
                    main_btn01.setBackgroundResource(R.drawable.back_azul_s)
                    color = 1
                    main_nombre.setText("")
                    cargarMaterias(getCorreo(), GRUPO)
                }else{
                    Toast.makeText(this, "solo puedes registrar 6 materias", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "faltan datos", Toast.LENGTH_SHORT).show()
            }
        }
        main_equis02.setOnClickListener {
            despintar()
            main_materias.visibility = View.GONE
        }

        main_confi.setOnClickListener {
            pintar()
            main_menu.visibility = View.VISIBLE
        }
        main_equis.setOnClickListener {
            despintar()
            main_menu.visibility = View.GONE
        }
        main_btn01.setOnClickListener {
            limpiar()
            main_btn01.setBackgroundResource(R.drawable.back_azul_s)
            color = 1
        }
        main_btn02.setOnClickListener {
            limpiar()
            main_btn02.setBackgroundResource(R.drawable.back_amarillo_s)
            color = 2
        }
        main_btn03.setOnClickListener {
            limpiar()
            main_btn03.setBackgroundResource(R.drawable.back_naranja_s)
            color = 3
        }
        main_btn04.setOnClickListener {
            limpiar()
            main_btn04.setBackgroundResource(R.drawable.back_verde_s)
            color = 4
        }
        main_btn05.setOnClickListener {
            limpiar()
            main_btn05.setBackgroundResource(R.drawable.back_verde_fuerte_s)
            color = 5
        }
        main_btn06.setOnClickListener {
            limpiar()
            main_btn06.setBackgroundResource(R.drawable.back_azul_claro_s)
            color = 6
        }

        home.setOnClickListener {

            val prefs = getSharedPreferences(getString(R.string.prefs_file_data_user), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            val intent: Intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }
        bloquear.setOnClickListener {
            if (bloqueado) {
                bloquear.setImageResource(R.drawable.ic_candado_01)
                bloqueado = false
            } else {
                bloquear.setImageResource(R.drawable.ic_candado_02)
                bloqueado = true
            }
        }

        //row_11.visibility = View.GONE
        //row_12.visibility = View.GONE

        c1.setOnClickListener { showDetalles(c1) }
        c2.setOnClickListener { showDetalles(c2) }
        c3.setOnClickListener { showDetalles(c3) }
        c4.setOnClickListener { showDetalles(c4) }
        c5.setOnClickListener { showDetalles(c5) }
        c6.setOnClickListener { showDetalles(c6) }
        c7.setOnClickListener { showDetalles(c7) }
        c8.setOnClickListener { showDetalles(c8) }
        c9.setOnClickListener { showDetalles(c9) }
        c10.setOnClickListener { showDetalles(c10) }
        c11.setOnClickListener { showDetalles(c11) }
        c12.setOnClickListener { showDetalles(c12) }
        c13.setOnClickListener { showDetalles(c13) }
        c14.setOnClickListener { showDetalles(c14) }
        c15.setOnClickListener { showDetalles(c15) }
        c16.setOnClickListener { showDetalles(c16) }
        c17.setOnClickListener { showDetalles(c17) }
        c18.setOnClickListener { showDetalles(c18) }
        c19.setOnClickListener { showDetalles(c19) }
        c20.setOnClickListener { showDetalles(c20) }
        c21.setOnClickListener { showDetalles(c21) }
        c22.setOnClickListener { showDetalles(c22) }
        c23.setOnClickListener { showDetalles(c23) }
        c24.setOnClickListener { showDetalles(c24) }
        c25.setOnClickListener { showDetalles(c25) }
        c26.setOnClickListener { showDetalles(c26) }
        c27.setOnClickListener { showDetalles(c27) }
        c28.setOnClickListener { showDetalles(c28) }
        c29.setOnClickListener { showDetalles(c29) }
        c30.setOnClickListener { showDetalles(c30) }
        c31.setOnClickListener { showDetalles(c31) }
        c32.setOnClickListener { showDetalles(c32) }
        c33.setOnClickListener { showDetalles(c33) }
        c34.setOnClickListener { showDetalles(c34) }
        c35.setOnClickListener { showDetalles(c35) }
        c36.setOnClickListener { showDetalles(c36) }
        c37.setOnClickListener { showDetalles(c37) }
        c38.setOnClickListener { showDetalles(c38) }
        c39.setOnClickListener { showDetalles(c39) }
        c40.setOnClickListener { showDetalles(c40) }
        c41.setOnClickListener { showDetalles(c41) }
        c42.setOnClickListener { showDetalles(c42) }
        c43.setOnClickListener { showDetalles(c43) }
        c44.setOnClickListener { showDetalles(c44) }
        c45.setOnClickListener { showDetalles(c45) }
        c46.setOnClickListener { showDetalles(c46) }
        c47.setOnClickListener { showDetalles(c47) }
        c48.setOnClickListener { showDetalles(c48) }
        c49.setOnClickListener { showDetalles(c49) }
        c50.setOnClickListener { showDetalles(c50) }
        c51.setOnClickListener { showDetalles(c51) }
        c52.setOnClickListener { showDetalles(c52) }
        c53.setOnClickListener { showDetalles(c53) }
        c54.setOnClickListener { showDetalles(c54) }
        c55.setOnClickListener { showDetalles(c55) }
        c56.setOnClickListener { showDetalles(c56) }
        c57.setOnClickListener { showDetalles(c57) }
        c58.setOnClickListener { showDetalles(c58) }
        c59.setOnClickListener { showDetalles(c59) }
        c60.setOnClickListener { showDetalles(c60) }

    }


    private fun showDetalles(caja: TextView) {
        if (!bloqueado) {
            pintar()
            main_materias.visibility = View.VISIBLE
            cajaTemp = caja
        }
        println("--------")
        println(caja.id.toString())
        println("--------")
    }

    private fun selection(fondo: LinearLayout, texto: TextView?) {

        val actualColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            (fondo.getBackground() as GradientDrawable).color?.defaultColor
        } else {
            TODO("VERSION.SDK_INT < N")
        }

        when (actualColor) {
            -11309570 -> {
                cajaTemp.setBackgroundResource(R.color.colorOnPrimary)
                cajaTemp.setText(texto?.text.toString())
                registrarPosition(texto?.text.toString(),cajaTemp.id,1)
            }
            -141259 -> {
                cajaTemp.setBackgroundResource(R.color.amarillo)
                cajaTemp.setText(texto?.text.toString())
                cajaTemp.setTextColor(Color.BLACK)
                registrarPosition(texto?.text.toString(),cajaTemp.id,2)
            }
            -21696 -> {
                cajaTemp.setBackgroundResource(R.color.naranja)
                cajaTemp.setText(texto?.text.toString())
                cajaTemp.setTextColor(Color.BLACK)
                registrarPosition(texto?.text.toString(),cajaTemp.id,3)
            }
            -11751600 -> {
                cajaTemp.setBackgroundResource(R.color.verde)
                cajaTemp.setText(texto?.text.toString())
                cajaTemp.setTextColor(Color.BLACK)
                registrarPosition(texto?.text.toString(),cajaTemp.id,4)
            }
            -16742021 -> {
                cajaTemp.setBackgroundResource(R.color.verde_fuerte)
                cajaTemp.setText(texto?.text.toString())
                cajaTemp.setTextColor(Color.WHITE)
                registrarPosition(texto?.text.toString(),cajaTemp.id,5)
            }
            -8211969 -> {
                cajaTemp.setBackgroundResource(R.color.azul_claro)
                cajaTemp.setText(texto?.text.toString())
                cajaTemp.setTextColor(Color.BLACK)
                registrarPosition(texto?.text.toString(),cajaTemp.id,6)
            }
            -9079435 -> {
                cajaTemp.setBackgroundResource(R.color.gris)
                cajaTemp.setText("")

                bd.collection("position")
                        .document("${getCorreo()}_${GRUPO}_${cajaTemp.id}").delete()
            }
            else -> { // Note the block
                cajaTemp.setBackgroundResource(R.color.gris)
                cajaTemp.setText("error :(")
            }

        }
        despintar()
        main_materias.visibility = View.GONE
    }

    private fun limpiar() {
        main_btn01.setBackgroundResource(back_azul1)
        main_btn02.setBackgroundResource(R.drawable.back_amarillo)
        main_btn03.setBackgroundResource(R.drawable.back_naranja)
        main_btn04.setBackgroundResource(R.drawable.back_verde)
        main_btn05.setBackgroundResource(R.drawable.back_verde_fuerte)
        main_btn06.setBackgroundResource(R.drawable.back_azul_claro)
    }

    private fun getCorreo(): String {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        var correo = prefs.getString("correo", "2021ff").toString()
        return correo
    }

    private fun pintar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.barra01)
    }

    private fun despintar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.colorPrimaryVariant)
    }

    private fun cargarMaterias(correo: String, grupo: String) {
        materias.clear()
        bd.collection("materias")
                .whereEqualTo("correo", "${correo}")
                .whereEqualTo("grupo", "${grupo}")
                .get().addOnSuccessListener { result ->

                    for (document in result) {
                        materias.add(materiaObj(
                                "${document.getString("materia").toString()}",
                                "${document.getString("color").toString()}"
                        ))
                    }
                    var i = 0;
                    while (i < materias.size) {
                        llenarmaterias(i, materias[i].color.toInt(), materias[i].nombre)
                        i++
                    }
                }
    }
    private fun ocultarmaterias(){
        main_fondo01.visibility = View.GONE
        main_fondo02.visibility = View.GONE
        main_fondo03.visibility = View.GONE
        main_fondo04.visibility = View.GONE
        main_fondo05.visibility = View.GONE
        main_fondo06.visibility = View.GONE
    }
    private fun llenarmaterias(position: Int, color: Int, cadena: String) {

        when (position) {
            0 -> {
                pintarFondo(main_fondo01, color)
                main_materia01.setText("${cadena}")
            }
            1 -> {
                pintarFondo(main_fondo02, color)
                main_materia02.setText("${cadena}")
            }
            2 -> {
                pintarFondo(main_fondo03, color)
                main_materia03.setText("${cadena}")
            }
            3 -> {
                pintarFondo(main_fondo04, color)
                main_materia04.setText("${cadena}")
            }
            4 -> {
                pintarFondo(main_fondo05, color)
                main_materia05.setText("${cadena}")
            }
            5 -> {
                pintarFondo(main_fondo06, color)
                main_materia06.setText("${cadena}")
            }
            else -> {
                Toast.makeText(this, "hay mas de 6 materias :( ${position}" , Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun pintarFondo(fondo: LinearLayout?, color: Int) {
        fondo?.visibility = View.VISIBLE
        when (color) {
            1 -> {
                fondo?.setBackgroundResource(R.drawable.back_azul)
            }
            2 -> {
                fondo?.setBackgroundResource(R.drawable.back_amarillo)
            }
            3 -> {
                fondo?.setBackgroundResource(R.drawable.back_naranja)
            }
            4 -> {
                fondo?.setBackgroundResource(R.drawable.back_verde)
            }
            5 -> {
                fondo?.setBackgroundResource(R.drawable.back_verde_fuerte)
            }
            6 -> {
                fondo?.setBackgroundResource(R.drawable.back_azul_claro)
            }
            else -> {
                fondo?.setBackgroundResource(R.drawable.thumb)
            }
        }
    }

    private fun registrarPosition( materia:String ,idCaja:Int,color:Int){
        bd.collection("position")
                .document("${getCorreo()}_${GRUPO}_${idCaja}").set(
                hashMapOf(
                        "correo" to getCorreo(),
                        "grupo" to GRUPO,
                        "materia" to "${materia}",
                        "idCaja" to "${idCaja}",
                        "color" to "${color}"
                ))
    }


    override fun onBackPressed() {
        //super.onBackPressed()
    }
}
