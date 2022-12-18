package com.example.e_commerceapp.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.R
import com.example.e_commerceapp.adapter.ItemAdapter
import com.example.e_commerceapp.adapter.ItemData
import com.example.e_commerceapp.databinding.FragmentWhislistBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WhislistFragment : Fragment() {

    lateinit var binding: FragmentWhislistBinding
    lateinit var recyclerView: RecyclerView
    var productList = ArrayList<ItemData>()
    val myAdapter = ItemAdapter(productList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWhislistBinding.inflate(layoutInflater)

        //Set RecyclerView
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = myAdapter
        



        return binding.root

    }


}