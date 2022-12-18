package com.example.e_commerceapp.category

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.R

class CategoryAdapter(val categoryList:ArrayList<CategoryData>):RecyclerView.Adapter<CategoryHolder>() {

    var onItemClick: ((CategoryData) -> Unit)? = null
    var post = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val v = LayoutInflater.from(parent.context)
        return CategoryHolder(v,parent)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(categoryList[position])

        val category = categoryList[position]
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(category)
            post = position
            notifyDataSetChanged()
        }
        if (post == position){
            holder.card.setCardBackgroundColor(Color.parseColor("#3A6BCC"))
        }else{
            holder.card.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
        }
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}