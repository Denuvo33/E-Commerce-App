package com.example.e_commerceapp.myproductholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.adapter.ItemData

class MyProductAdapter(val product:ArrayList<ItemData>):RecyclerView.Adapter<MyProductHolder>() {

    var onDelete:((ItemData) -> Unit) ? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProductHolder {
        val v = LayoutInflater.from(parent.context)
        return MyProductHolder(v,parent)
    }

    override fun onBindViewHolder(holder: MyProductHolder, position: Int) {
        holder.bind(product[position])
        var itemData = product[position]
        holder.deletebtn.setOnClickListener {
            onDelete?.invoke(itemData)
        }

    }

    override fun getItemCount(): Int {
        return product.size
    }
}