package com.example.e_commerceapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.NonDisposableHandle.parent


class ItemAdapter(val productList:ArrayList<ItemData>):RecyclerView.Adapter<ViewHolder>() {

    var onItemClick : ((ItemData) -> Unit) ? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
        return ViewHolder(v,parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productList[position])
        val product = productList[position]
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(product)
        }
        holder.whislit.setOnClickListener {
            onItemClick?.invoke(product)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}