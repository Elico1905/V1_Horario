package com.example.horario.objetos

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.horario.Home
import com.example.horario.Login
import com.example.horario.MainActivity
import com.example.horario.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.items_recycler_view.view.*

class MainAdapterAdmin(private val context: Context) :
    RecyclerView.Adapter<MainAdapterAdmin.MainViewHolderAdmin>() {
    private var datalist = mutableListOf<grupoObj>()

    private val bd = FirebaseFirestore.getInstance()

    fun setListData(data: MutableList<grupoObj>) {
        datalist = data
    }

    fun returnListData(): MutableList<grupoObj> {
        return datalist;
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolderAdmin {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.items_recycler_view, parent, false)
        return MainViewHolderAdmin(view)
    }

    override fun getItemCount(): Int {
        return if (datalist.size > 0) {
            datalist.size
        } else {
            0
        }
    }


    override fun onBindViewHolder(holder: MainViewHolderAdmin, position: Int) {
        var grupoObj = datalist[position]

        bd.collection("materias")
            .whereEqualTo("correo", "${grupoObj.correo}")
            .whereEqualTo("grupo", "${grupoObj.nombre}")
            .get().addOnSuccessListener { result ->
                holder.itemView.recycler_nuMaterias.text = "${result.size()}"
                grupoObj.numeroMaterias = "${holder.itemView.recycler_nuMaterias.text}".toInt()
            }

        bd.collection("position")
            .whereEqualTo("correo", "${grupoObj.correo}")
            .whereEqualTo("grupo", "${grupoObj.nombre}")
            .get().addOnSuccessListener { result ->
                holder.itemView.recycler_horas.text = "${result.size()}"
                grupoObj.horas = "${holder.itemView.recycler_horas.text}".toInt()
            }

        holder.itemView.recycler_carta.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("grupo", grupoObj.nombre)
            context.startActivity(intent)
        }

        holder.itemView.recycler_eliminar.setOnClickListener {
            holder.itemView.recycler_mensaje.visibility = View.VISIBLE
        }

        holder.itemView.recycler_btn_canceclar.setOnClickListener {
            holder.itemView.recycler_mensaje.visibility = View.GONE
        }

        holder.itemView.recycler_btn_eliminar.setOnClickListener {
            bd.collection("grupos")
                .document("${grupoObj.correo}_${grupoObj.nombre}").delete()
            holder.itemView.recycler_mensaje.visibility = View.GONE

            datalist.removeAt(position)
            notifyDataSetChanged()

            if(context is Home){
                if (!(context.lista.size > 0)){
                    context.home_mensaje.visibility = View.VISIBLE
                }

            }

        }



        holder.bindView(grupoObj)
    }


    inner class MainViewHolderAdmin(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(grupo: grupoObj) {
            itemView.recycler_grupo.text = "${grupo.nombre}"
            itemView.recycler_nuMaterias.text = "${grupo.numeroMaterias}"
            itemView.recycler_horas.text = "${grupo.horas}"
        }
    }

}