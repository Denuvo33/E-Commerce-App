package com.example.e_commerceapp.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.e_commerceapp.R

class ViewHolder(inflater: LayoutInflater,parent:ViewGroup):RecyclerView.ViewHolder(inflater.inflate(R.layout.item_list,parent,false)) {

    val title: TextView = itemView.findViewById(R.id.txt_product)
    val price:TextView = itemView.findViewById(R.id.txt_price)
    val img : ImageView = itemView.findViewById(R.id.product_img)
    val whislit : ImageView = itemView.findViewById(R.id.saveproduct)

    fun bind(data: ItemData){
        title.text = data.name
        price.text = data.price
        img.load(data.url)
    }

}