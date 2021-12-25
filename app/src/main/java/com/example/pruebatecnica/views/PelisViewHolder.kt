package com.example.pruebatecnica.views

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebatecnica.databinding.ItemPeliBinding
import com.squareup.picasso.Picasso

class PelisViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val binding = ItemPeliBinding.bind(view)
    fun bind(imagen:String){
        Picasso.get().load(imagen).into(binding.imgPeli)
    }

}