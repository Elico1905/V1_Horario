package com.example.horario.objetos

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.horario.Login
import com.example.horario.R
import kotlinx.android.synthetic.main.items_recycler_view.view.*

class MainAdapterAdmin (private val context: Context): RecyclerView.Adapter<MainAdapterAdmin.MainViewHolderAdmin>() {
    private var datalist = mutableListOf<grupoObj>();

    fun setListData(data:MutableList<grupoObj>){datalist = data}

    fun returnListData():MutableList<grupoObj>{ return datalist;}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolderAdmin {
        val view: View = LayoutInflater.from(context).inflate(R.layout.items_recycler_view,parent,false)
        return MainViewHolderAdmin(view)
    }
    override fun getItemCount(): Int { return if(datalist.size>0){ datalist.size}else{ 0 } }


    override fun onBindViewHolder(holder: MainViewHolderAdmin, position: Int) {
        var grupoObj = datalist[position]
        holder.itemView.recycler_carta.setOnClickListener {
            Toast.makeText(holder.itemView.context, "tocaste", Toast.LENGTH_SHORT).show()
        }
        holder.bindView(grupoObj)
    }

    inner class MainViewHolderAdmin (itemView: View):RecyclerView.ViewHolder(itemView){
        fun bindView(grupo: grupoObj){
            itemView.recycler_grupo.text = "${grupo.nombre}"
            itemView.recycler_nuMaterias.text = "${grupo.numeroMaterias}"
            itemView.recycler_horas.text = "${grupo.horas}"
        }
    }

}