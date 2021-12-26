package com.example.pruebatecnica.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebatecnica.R
import com.example.pruebatecnica.views.PelisViewHolder

class PelisAdapter(val imagenes:List<String>, val titulo:List<String>, val descripcion:List<String>):RecyclerView.Adapter<PelisViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PelisViewHolder {
      val layoutInflater = LayoutInflater.from(parent.context)
        return PelisViewHolder(layoutInflater.inflate(R.layout.item_peli,parent, false))
    }

    override fun onBindViewHolder(holder: PelisViewHolder, position: Int) {
        val item = imagenes[position]
        val titulo = titulo[position]
        val desc = descripcion[position]
        holder.bind(item,titulo, desc)
    }

    override fun getItemCount(): Int {
        return imagenes.size
    }


}