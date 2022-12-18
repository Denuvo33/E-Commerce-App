package com.example.e_commerceapp.myproductholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.e_commerceapp.R
import com.example.e_commerceapp.adapter.ItemData

class MyProductHolder(inflater: LayoutInflater,parent:ViewGroup):RecyclerView.ViewHolder(inflater.inflate(
    R.layout.my_productlist,parent,false)) {

    val title: TextView = itemView.findViewById(R.id.txt_myproduct)
    val price: TextView = itemView.findViewById(R.id.txt_myprice)
    val img : ImageView = itemView.findViewById(R.id.myproduct_img)
    val deletebtn : ImageView = itemView.findViewById(R.id.deleteproduct)


    fun bind(data: ItemData){
        title.text = data.name
        price.text = data.price
        img.load(data.url)
    }

}