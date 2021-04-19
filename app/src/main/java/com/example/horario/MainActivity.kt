package com.example.horario

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.horario.objetos.materiaObj
import com.example.horario.objetos.positionObj
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import com.example.horario.R.drawable.back_azul as back_azul1

class MainActivity : AppCompatActivity() {

    private var bloqueado: Boolean = true
    private var ID: String = "null"
    private var GRUPO: String = ""
    private var color: Int = 0

    private val bd = FirebaseFirestore.getInstance()
    private lateinit var cajaTemp: TextView

    private var materias = mutableListOf<materiaObj>()
    private var listacolor = mutableListOf<String>()
    private var listaPosition = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_main)
        pintar()

        val objintent: Intent = intent
        var grupo: String? = objintent.getStringExtra("grupo")
        GRUPO = grupo.toString()
        main_grupo.text = grupo

        cargarMaterias(getCorreo(), GRUPO)
        cargarMateriasPosition(getCorreo(), GRUPO)

        despintar()
        main_cargando.visibility = View.GONE

        val prefs = getSharedPreferences(
                getString(R.string.prefs_file_data_user),
                Context.MODE_PRIVATE
        ).edit()
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
        main_fondo08.setOnClickListener {
            selection(main_fondo08, main_materia08)
        }
        main_fondo09.setOnClickListener {
            selection(main_fondo06, main_materia09)
        }

        main_fondo0r.setOnClickListener {
            selection(main_fondo0r, main_materia0r)
        }

        main_agregar.setOnClickListener {
            main_nombre.clearFocus()
            if (main_nombre.text.isNotEmpty()) {
                if (validarSelectionColor()) {
                    if (main_fondo08.visibility == View.GONE) {
                        bd.collection("materias")
                                .document("${getCorreo()}_${GRUPO}_${main_nombre.text.toString()}").set(
                                        hashMapOf(
                                                "materia" to getMateria(main_nombre.text.toString()),
                                                "correo" to getCorreo(),
                                                "grupo" to GRUPO,
                                                "color" to "${color}"
                                        )
                                )
                        validar_color(color)
                        Toast.makeText(this, "Materia agregada", Toast.LENGTH_SHORT).show()
                        limpiar()
                        ocultarmaterias();
                        main_nombre.setText("")
                        cargarMaterias(getCorreo(), GRUPO)
                    } else {
                        Toast.makeText(this, "solo puedes registrar 8 materias", Toast.LENGTH_SHORT)
                                .show()
                    }
                }else{
                    Toast.makeText(this, "selecciona un color", Toast.LENGTH_SHORT).show()
                }


            } else {
                Toast.makeText(this, "faltan datos", Toast.LENGTH_SHORT).show()
            }
        }
        main_equis02.setOnClickListener {
            despintar()
            main_materias.visibility = View.GONE
            main_barra.fullScroll(ScrollView.FOCUS_UP)
        }

        main_bajar.setOnClickListener {
            main_barra.fullScroll(ScrollView.FOCUS_DOWN)
        }

        main_subir.setOnClickListener {
            main_barra.fullScroll(ScrollView.FOCUS_UP)
        }

        main_confi.setOnClickListener {
            pintar()
            main_menu.visibility = View.VISIBLE
        }
        main_equis.setOnClickListener {
            despintar()
            main_menu.visibility = View.GONE
            limpiar()
            main_nombre.setText("")
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
        main_btn07.setOnClickListener {
            limpiar()
            main_btn07.setBackgroundResource(R.drawable.back_rojo_s)
            color = 7
        }
        main_btn08.setOnClickListener {
            limpiar()
            main_btn08.setBackgroundResource(R.drawable.back_morado_s)
            color = 8
        }
        main_btn09.setOnClickListener {
            limpiar()
            main_btn09.setBackgroundResource(R.drawable.back_cafe_s)
            color = 9
        }

        home_btn_go_cancelar.setOnClickListener {
            despintar()
            main_salir.visibility = View.GONE
        }
        home_btn_go_ir.setOnClickListener {
            val prefs = getSharedPreferences(
                    getString(R.string.prefs_file_data_user),
                    Context.MODE_PRIVATE
            ).edit()
            prefs.clear()
            prefs.apply()

            val intent: Intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }
        home.setOnClickListener {
            pintar()
            main_salir.visibility = View.VISIBLE
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

        c1.setOnClickListener { showDetalles(c1, "c1") }
        c2.setOnClickListener { showDetalles(c2, "c2") }
        c3.setOnClickListener { showDetalles(c3, "c3") }
        c4.setOnClickListener { showDetalles(c4, "c4") }
        c5.setOnClickListener { showDetalles(c5, "c5") }
        c6.setOnClickListener { showDetalles(c6, "c6") }
        c7.setOnClickListener { showDetalles(c7, "c7") }
        c8.setOnClickListener { showDetalles(c8, "c8") }
        c9.setOnClickListener { showDetalles(c9, "c9") }
        c10.setOnClickListener { showDetalles(c10, "c10") }
        c11.setOnClickListener { showDetalles(c11, "c11") }
        c12.setOnClickListener { showDetalles(c12, "c12") }
        c13.setOnClickListener { showDetalles(c13, "c13") }
        c14.setOnClickListener { showDetalles(c14, "c14") }
        c15.setOnClickListener { showDetalles(c15, "c15") }
        c16.setOnClickListener { showDetalles(c16, "c16") }
        c17.setOnClickListener { showDetalles(c17, "c17") }
        c18.setOnClickListener { showDetalles(c18, "c18") }
        c19.setOnClickListener { showDetalles(c19, "c19") }
        c20.setOnClickListener { showDetalles(c20, "c20") }
        c21.setOnClickListener { showDetalles(c21, "c21") }
        c22.setOnClickListener { showDetalles(c22, "c22") }
        c23.setOnClickListener { showDetalles(c23, "c23") }
        c24.setOnClickListener { showDetalles(c24, "c24") }
        c25.setOnClickListener { showDetalles(c25, "c25") }
        c26.setOnClickListener { showDetalles(c26, "c26") }
        c27.setOnClickListener { showDetalles(c27, "c27") }
        c28.setOnClickListener { showDetalles(c28, "c28") }
        c29.setOnClickListener { showDetalles(c29, "c29") }
        c30.setOnClickListener { showDetalles(c30, "c30") }
        c31.setOnClickListener { showDetalles(c31, "c31") }
        c32.setOnClickListener { showDetalles(c32, "c32") }
        c33.setOnClickListener { showDetalles(c33, "c33") }
        c34.setOnClickListener { showDetalles(c34, "c34") }
        c35.setOnClickListener { showDetalles(c35, "c35") }
        c36.setOnClickListener { showDetalles(c36, "c36") }
        c37.setOnClickListener { showDetalles(c37, "c37") }
        c38.setOnClickListener { showDetalles(c38, "c38") }
        c39.setOnClickListener { showDetalles(c39, "c39") }
        c40.setOnClickListener { showDetalles(c40, "c40") }
        c41.setOnClickListener { showDetalles(c41, "c41") }
        c42.setOnClickListener { showDetalles(c42, "c42") }
        c43.setOnClickListener { showDetalles(c43, "c43") }
        c44.setOnClickListener { showDetalles(c44, "c44") }
        c45.setOnClickListener { showDetalles(c45, "c45") }
        c46.setOnClickListener { showDetalles(c46, "c46") }
        c47.setOnClickListener { showDetalles(c47, "c47") }
        c48.setOnClickListener { showDetalles(c48, "c48") }
        c49.setOnClickListener { showDetalles(c49, "c49") }
        c50.setOnClickListener { showDetalles(c50, "c50") }
        c51.setOnClickListener { showDetalles(c51, "c51") }
        c52.setOnClickListener { showDetalles(c52, "c52") }
        c53.setOnClickListener { showDetalles(c53, "c53") }
        c54.setOnClickListener { showDetalles(c54, "c54") }
        c55.setOnClickListener { showDetalles(c55, "c55") }
        c56.setOnClickListener { showDetalles(c56, "c56") }
        c57.setOnClickListener { showDetalles(c57, "c57") }
        c58.setOnClickListener { showDetalles(c58, "c58") }
        c59.setOnClickListener { showDetalles(c59, "c59") }
        c60.setOnClickListener { showDetalles(c60, "c60") }

    }

    private fun getMateria(materia: String): String {
        var aux: String = ""
        var i: Int = 1
        while (i < materia.length) {
            aux += materia[i]
            i++
        }
        return "${materia.get(0).toUpperCase()}${aux.toLowerCase()}"
    }


    private fun showDetalles(caja: TextView, id: String) {
        if (!bloqueado) {
            pintar()
            main_materias.visibility = View.VISIBLE
            cajaTemp = caja
            ID = id
        }
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
                registrarPosition(texto?.text.toString(), 1)
            }
            -141259 -> {
                cajaTemp.setBackgroundResource(R.color.amarillo)
                cajaTemp.setText(texto?.text.toString())
                cajaTemp.setTextColor(Color.BLACK)
                registrarPosition(texto?.text.toString(), 2)
            }
            -21696 -> {
                cajaTemp.setBackgroundResource(R.color.naranja)
                cajaTemp.setText(texto?.text.toString())
                cajaTemp.setTextColor(Color.BLACK)
                registrarPosition(texto?.text.toString(), 3)
            }
            -11751600 -> {
                cajaTemp.setBackgroundResource(R.color.verde)
                cajaTemp.setText(texto?.text.toString())
                cajaTemp.setTextColor(Color.BLACK)
                registrarPosition(texto?.text.toString(), 4)
            }
            -16742021 -> {
                cajaTemp.setBackgroundResource(R.color.verde_fuerte)
                cajaTemp.setText(texto?.text.toString())
                cajaTemp.setTextColor(Color.WHITE)
                registrarPosition(texto?.text.toString(), 5)
            }
            -8211969 -> {
                cajaTemp.setBackgroundResource(R.color.azul_claro)
                cajaTemp.setText(texto?.text.toString())
                cajaTemp.setTextColor(Color.BLACK)
                registrarPosition(texto?.text.toString(), 6)
            }
            -446124 -> {
                cajaTemp.setBackgroundResource(R.color.rojo)
                cajaTemp.setText(texto?.text.toString())
                cajaTemp.setTextColor(Color.WHITE)
                registrarPosition(texto?.text.toString(), 7)
            }
            -5096195 -> {
                cajaTemp.setBackgroundResource(R.color.morado)
                cajaTemp.setText(texto?.text.toString())
                cajaTemp.setTextColor(Color.WHITE)
                registrarPosition(texto?.text.toString(), 8)
            }
            -6195622 -> {
                cajaTemp.setBackgroundResource(R.color.cafe)
                cajaTemp.setText(texto?.text.toString())
                cajaTemp.setTextColor(Color.WHITE)
                registrarPosition(texto?.text.toString(), 9)
                Toast.makeText(this, "${ID}", Toast.LENGTH_SHORT).show()
            }

            -9079435 -> {
                cajaTemp.setBackgroundResource(R.color.gris)
                cajaTemp.setText("")
                bd.collection("position")
                        .document("${getCorreo()}_${GRUPO}_${ID}").delete()
            }

            else -> {
                cajaTemp.setBackgroundResource(R.color.gris)
                cajaTemp.setText("error :(")
            }

        }
        main_barra.fullScroll(ScrollView.FOCUS_UP)
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
        main_btn07.setBackgroundResource(R.drawable.back_rojo)
        main_btn08.setBackgroundResource(R.drawable.back_morado)
        main_btn09.setBackgroundResource(R.drawable.back_cafe)
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
        window.statusBarColor =
                ContextCompat.getColor(applicationContext, R.color.colorPrimaryVariant)
    }

    private fun cargarMaterias(correo: String, grupo: String) {
        materias.clear()
        bd.collection("materias")
                .whereEqualTo("correo", "${correo}")
                .whereEqualTo("grupo", "${grupo}")
                .get().addOnSuccessListener { result ->

                    for (document in result) {
                        materias.add(
                                materiaObj(
                                        "${document.getString("materia").toString()}",
                                        "${document.getString("color").toString()}"
                                )
                        )
                    }
                    var i = 0;
                    while (i < materias.size) {
                        llenarmaterias(i, materias[i].color.toInt(), materias[i].nombre)

                        i++
                    }
                }
    }

    private fun ocultarmaterias() {
        main_fondo01.visibility = View.GONE
        main_fondo02.visibility = View.GONE
        main_fondo03.visibility = View.GONE
        main_fondo04.visibility = View.GONE
        main_fondo05.visibility = View.GONE
        main_fondo06.visibility = View.GONE
    }

    private fun llenarmaterias(position: Int, color: Int, cadena: String) {
        validar_color(color)
        when (position) {
            0 -> {
                pintarFondo(main_fondo01, color, main_materia01)
                main_materia01.setText("${cadena}")
            }
            1 -> {
                pintarFondo(main_fondo02, color, main_materia02)
                main_materia02.setText("${cadena}")
            }
            2 -> {
                pintarFondo(main_fondo03, color, main_materia03)
                main_materia03.setText("${cadena}")
            }
            3 -> {
                pintarFondo(main_fondo04, color, main_materia04)
                main_materia04.setText("${cadena}")
            }
            4 -> {
                pintarFondo(main_fondo05, color, main_materia05)
                main_materia05.setText("${cadena}")
            }
            5 -> {
                pintarFondo(main_fondo06, color, main_materia06)
                main_materia06.setText("${cadena}")
            }
            6 -> {
                pintarFondo(main_fondo07, color, main_materia07)
                main_materia07.setText("${cadena}")
            }
            7 -> {
                pintarFondo(main_fondo08, color, main_materia08)
                main_materia08.setText("${cadena}")
            }
            8 -> {
                pintarFondo(main_fondo09, color, main_materia09)
                main_materia09.setText("${cadena}")
            }


            else -> {
                //Toast.makeText(this, "hay mas de 6 materias :( ${position}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun pintarFondo(fondo: LinearLayout?, color: Int, caja: TextView) {
        fondo?.visibility = View.VISIBLE
        when (color) {
            1 -> {
                fondo?.setBackgroundResource(R.drawable.back_azul)
                caja?.setTextColor(Color.WHITE)
            }
            2 -> {
                fondo?.setBackgroundResource(R.drawable.back_amarillo)
                caja?.setTextColor(Color.BLACK)
            }
            3 -> {
                fondo?.setBackgroundResource(R.drawable.back_naranja)
                caja?.setTextColor(Color.BLACK)
            }
            4 -> {
                fondo?.setBackgroundResource(R.drawable.back_verde)
                caja?.setTextColor(Color.WHITE)
            }
            5 -> {
                fondo?.setBackgroundResource(R.drawable.back_verde_fuerte)
                caja?.setTextColor(Color.WHITE)
            }
            6 -> {
                fondo?.setBackgroundResource(R.drawable.back_azul_claro)
                caja?.setTextColor(Color.BLACK)
            }
            7 -> {
                fondo?.setBackgroundResource(R.drawable.back_rojo)
                caja?.setTextColor(Color.WHITE)
            }
            8 -> {
                fondo?.setBackgroundResource(R.drawable.back_morado)
                caja?.setTextColor(Color.WHITE)
            }
            9 -> {
                fondo?.setBackgroundResource(R.drawable.back_cafe)
                caja?.setTextColor(Color.WHITE)
            }
            else -> {
                fondo?.setBackgroundResource(R.drawable.thumb)
            }
        }
    }

    private fun registrarPosition(materia: String, color: Int) {
        bd.collection("position")
                .document("${getCorreo()}_${GRUPO}_${ID}").set(
                        hashMapOf(
                                "correo" to getCorreo(),
                                "grupo" to GRUPO,
                                "materia" to "${materia}",
                                "idCaja" to "${ID}",
                                "color" to "${color}"
                        )
                )
    }

    private fun cargarMateriasPosition(correo: String, grupo: String) {
        bd.collection("position")
                .whereEqualTo("correo", "${correo}")
                .whereEqualTo("grupo", "${grupo}")
                .get().addOnSuccessListener { result ->
                    for (document in result) {
                        colocarData(
                                document.getString("idCaja").toString(),
                                document.getString("color").toString(),
                                document.getString("materia").toString()
                        )
                    }

                }
    }

    private fun colocarData(id: String, aux2: String, materia: String) {
        var color: Int = aux2.toInt()

         listaPosition.add(id)
        println("-----\nlista agregado")
        println(listaPosition)
        println("-----")
        when (id) {
            "c1" -> {
                c1.text = "${materia}"
                colocarColor(color, c1)
            }
            "c2" -> {
                c2.text = "${materia}"
                colocarColor(color, c2)
            }
            "c3" -> {
                c3.text = "${materia}"
                colocarColor(color, c3)
            }
            "c4" -> {
                c4.text = "${materia}"
                colocarColor(color, c4)
            }
            "c5" -> {
                c5.text = "${materia}"
                colocarColor(color, c5)
            }
            "c6" -> {
                c6.text = "${materia}"
                colocarColor(color, c6)
            }
            "c7" -> {
                c7.text = "${materia}"
                colocarColor(color, c7)
            }
            "c8" -> {
                c8.text = "${materia}"
                colocarColor(color, c8)
            }
            "c9" -> {
                c9.text = "${materia}"
                colocarColor(color, c9)
            }
            "c10" -> {
                c10.text = "${materia}"
                colocarColor(color, c10)
            }
            "c11" -> {
                c11.text = "${materia}"
                colocarColor(color, c11)
            }
            "c12" -> {
                c12.text = "${materia}"
                colocarColor(color, c12)
            }
            "c13" -> {
                c13.text = "${materia}"
                colocarColor(color, c13)
            }
            "c14" -> {
                c14.text = "${materia}"
                colocarColor(color, c14)
            }
            "c15" -> {
                c15.text = "${materia}"
                colocarColor(color, c15)
            }
            "c16" -> {
                c16.text = "${materia}"
                colocarColor(color, c16)
            }
            "c17" -> {
                c17.text = "${materia}"
                colocarColor(color, c17)
            }
            "c18" -> {
                c18.text = "${materia}"
                colocarColor(color, c18)
            }
            "c19" -> {
                c19.text = "${materia}"
                colocarColor(color, c19)
            }
            "c20" -> {
                c20.text = "${materia}"
                colocarColor(color, c20)
            }
            "c21" -> {
                c21.text = "${materia}"
                colocarColor(color, c21)
            }
            "c22" -> {
                c22.text = "${materia}"
                colocarColor(color, c22)
            }
            "c23" -> {
                c23.text = "${materia}"
                colocarColor(color, c23)
            }
            "c24" -> {
                c24.text = "${materia}"
                colocarColor(color, c24)
            }
            "c25" -> {
                c25.text = "${materia}"
                colocarColor(color, c25)
            }
            "c26" -> {
                c26.text = "${materia}"
                colocarColor(color, c26)
            }
            "c27" -> {
                c27.text = "${materia}"
                colocarColor(color, c27)
            }
            "c28" -> {
                c28.text = "${materia}"
                colocarColor(color, c28)
            }
            "c29" -> {
                c29.text = "${materia}"
                colocarColor(color, c29)
            }
            "c30" -> {
                c30.text = "${materia}"
                colocarColor(color, c30)
            }
            "c31" -> {
                c31.text = "${materia}"
                colocarColor(color, c31)
            }
            "c32" -> {
                c32.text = "${materia}"
                colocarColor(color, c32)
            }
            "c33" -> {
                c33.text = "${materia}"
                colocarColor(color, c33)
            }
            "c34" -> {
                c34.text = "${materia}"
                colocarColor(color, c34)
            }
            "c35" -> {
                c35.text = "${materia}"
                colocarColor(color, c35)
            }
            "c36" -> {
                c36.text = "${materia}"
                colocarColor(color, c36)
            }
            "c37" -> {
                c37.text = "${materia}"
                colocarColor(color, c37)
            }
            "c38" -> {
                c38.text = "${materia}"
                colocarColor(color, c38)
            }
            "c39" -> {
                c39.text = "${materia}"
                colocarColor(color, c39)
            }
            "c40" -> {
                c40.text = "${materia}"
                colocarColor(color, c40)
            }
            "c41" -> {
                c41.text = "${materia}"
                colocarColor(color, c41)
            }
            "c42" -> {
                c42.text = "${materia}"
                colocarColor(color, c42)
            }
            "c43" -> {
                c43.text = "${materia}"
                colocarColor(color, c43)
            }
            "c44" -> {
                c44.text = "${materia}"
                colocarColor(color, c44)
            }
            "c45" -> {
                c45.text = "${materia}"
                colocarColor(color, c45)
            }
            "c46" -> {
                c46.text = "${materia}"
                colocarColor(color, c46)
            }
            "c47" -> {
                c47.text = "${materia}"
                colocarColor(color, c47)
            }
            "c48" -> {
                c48.text = "${materia}"
                colocarColor(color, c48)
            }
            "c49" -> {
                c49.text = "${materia}"
                colocarColor(color, c49)
            }
            "c50" -> {
                c50.text = "${materia}"
                colocarColor(color, c50)
            }
            "c51" -> {
                c51.text = "${materia}"
                colocarColor(color, c51)
            }
            "c52" -> {
                c52.text = "${materia}"
                colocarColor(color, c52)
            }
            "c53" -> {
                c53.text = "${materia}"
                colocarColor(color, c53)
            }
            "c54" -> {
                c54.text = "${materia}"
                colocarColor(color, c54)
            }
            "c55" -> {
                c55.text = "${materia}"
                colocarColor(color, c55)
            }
            "c56" -> {
                c56.text = "${materia}"
                colocarColor(color, c56)
            }
            "c57" -> {
                c57.text = "${materia}"
                colocarColor(color, c57)
            }
            "c58" -> {
                c58.text = "${materia}"
                colocarColor(color, c58)
            }
            "c59" -> {
                c59.text = "${materia}"
                colocarColor(color, c59)
            }
            "c60" -> {
                c60.text = "${materia}"
                colocarColor(color, c60)
            }
            else -> {
                //Toast.makeText(this, "algo salio mal", Toast.LENGTH_SHORT).show()
                bd.collection("position").document("${getCorreo()}_${GRUPO}_${id}").delete()
            }
        }
    }

    private fun colocarColor(color: Int, contenedor: TextView) {
        when (color) {
            1 -> {
                contenedor?.setBackgroundResource(R.color.colorOnPrimary)
                contenedor.setTextColor(Color.WHITE)
            }
            2 -> {
                contenedor?.setBackgroundResource(R.color.amarillo)
                contenedor.setTextColor(Color.BLACK)
            }
            3 -> {
                contenedor?.setBackgroundResource(R.color.naranja)
                contenedor.setTextColor(Color.BLACK)
            }
            4 -> {
                contenedor?.setBackgroundResource(R.color.verde)
                contenedor.setTextColor(Color.BLACK)
            }
            5 -> {
                contenedor?.setBackgroundResource(R.color.verde_fuerte)
                contenedor.setTextColor(Color.WHITE)
            }
            6 -> {
                contenedor?.setBackgroundResource(R.color.azul_claro)
                contenedor.setTextColor(Color.BLACK)
            }
            7 -> {
                contenedor?.setBackgroundResource(R.color.rojo)
                contenedor.setTextColor(Color.WHITE)
            }
            8 -> {
                contenedor?.setBackgroundResource(R.color.morado)
                contenedor.setTextColor(Color.WHITE)
            }
            9 -> {
                contenedor?.setBackgroundResource(R.color.cafe)
                contenedor.setTextColor(Color.WHITE)
            }
            else -> {
                contenedor?.setBackgroundResource(R.color.gris)
                contenedor.setTextColor(Color.WHITE)
            }
        }

    }

    override fun onBackPressed() {
        //super.onBackPressed()

        if (main_menu.visibility == View.VISIBLE || main_materias.visibility == View.VISIBLE || main_salir.visibility == View.VISIBLE) {
            if (main_menu.visibility == View.VISIBLE) {
                main_menu.visibility = View.GONE
                limpiar()
                main_nombre.setText("")
            }

            if (main_materias.visibility == View.VISIBLE) {
                main_materias.visibility = View.GONE
                main_barra.fullScroll(ScrollView.FOCUS_UP)
            }
            if (main_salir.visibility == View.VISIBLE) {
                main_salir.visibility = View.GONE
            }
            despintar()
        } else {
            if (main_salir.visibility == View.GONE) {
                main_salir.visibility = View.VISIBLE
                pintar()
            }
        }

    }

    private fun validar_color(color: Int) {
        listacolor.add("${color}")
        when (color) {
            1 -> {
                main_btn01.visibility = View.GONE
            }
            2 -> {
                main_btn02.visibility = View.GONE
            }
            3 -> {
                main_btn03.visibility = View.GONE
            }
            4 -> {
                main_btn04.visibility = View.GONE
            }
            5 -> {
                main_btn05.visibility = View.GONE
            }
            6 -> {
                main_btn06.visibility = View.GONE
            }
            7 -> {
                main_btn07.visibility = View.GONE
            }
            8 -> {
                main_btn08.visibility = View.GONE
            }
            9 -> {
                main_btn09.visibility = View.GONE
            }
        }
        this.color = 0
    }

    private fun validarSelectionColor(): Boolean {
        return color != 0
    }
}
