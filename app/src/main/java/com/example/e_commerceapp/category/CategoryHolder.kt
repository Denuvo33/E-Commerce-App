package com.example.e_commerceapp.category

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.R
import com.google.android.material.card.MaterialCardView

class CategoryHolder(val inflater: LayoutInflater,parent:ViewGroup):RecyclerView.ViewHolder(inflater.inflate(R.layout.category_list,parent,false)) {

    val txtCategory:TextView = itemView.findViewById(R.id.txt_category)
    val imgCategory:ImageView = itemView.findViewById(R.id.ctgr_img)
    val card:MaterialCardView = itemView.findViewById(R.id.card)

    fun bind(data: CategoryData){
        txtCategory.text = data.category
        imgCategory.setImageResource(data.img)
    }

}