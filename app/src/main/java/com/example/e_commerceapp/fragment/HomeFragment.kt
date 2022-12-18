package com.example.e_commerceapp.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.MainActivity
import com.example.e_commerceapp.ProductFullActivity
import com.example.e_commerceapp.R
import com.example.e_commerceapp.ViewModel
import com.example.e_commerceapp.adapter.ItemAdapter
import com.example.e_commerceapp.adapter.ItemData
import com.example.e_commerceapp.category.CategoryAdapter
import com.example.e_commerceapp.category.CategoryData
import com.example.e_commerceapp.databinding.FragmentHomeBinding
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.gson.Gson
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var recyclerView: RecyclerView
    var productList = ArrayList<ItemData>()
    var productFilter = ArrayList<ItemData>()
    lateinit var itemAdapter: ItemAdapter
    lateinit var db:FirebaseFirestore
    val categoryList = ArrayList<CategoryData>()
    val categoryAdapter = CategoryAdapter(categoryList)
    var filter : List<ItemData> = listOf()
    var isFilterAll = true




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)

        //Set RecyclerView category
        binding.categoryRecyler.adapter = categoryAdapter
        binding.categoryRecyler.layoutManager = GridLayoutManager(context,4)

        //OnRecyclerView Click
        /*  itemAdapter.onItemClick = {


               val intent = Intent(requireActivity(),ProductFullActivity::class.java)
               intent.putExtra("product",it)
               startActivity(intent)
           }*/

        //Set SearchView
        binding.searchBar.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isNotEmpty()){
                    productFilter.clear()
                    val search = newText.toLowerCase(Locale.getDefault())
                    productList.forEach{
                        if (it.name!!.toLowerCase(Locale.getDefault()).contains(search)){
                            productFilter.add(it)
                        }
                    }
                    itemAdapter.notifyDataSetChanged()
                }
                else{
                    if (isFilterAll){
                        productFilter.clear()
                        productFilter.addAll(productList)
                        itemAdapter.notifyDataSetChanged()
                    }else{
                        productFilter.clear()
                        productFilter.addAll(filter)
                        itemAdapter.notifyDataSetChanged()
                    }

                }
                return true
            }

        })


        //Set RecyclerView Product
        itemAdapter = ItemAdapter(productFilter)
        recyclerView = binding.recyclerView
        recyclerView.adapter = itemAdapter
        recyclerView.layoutManager = GridLayoutManager(context,2)

        //SetItemClick
        itemAdapter.onItemClick = {
            val intent = Intent(requireActivity(),ProductFullActivity::class.java)
            intent.putExtra("product",it)
            startActivity(intent)
        }



        //FilterCategory
        categoryAdapter.onItemClick = {category ->

            Log.d("TAG","category =${category.category}")

            if (category.category == "All"){
                productFilter.clear()
                isFilterAll = true
                productFilter.addAll(productList)
                itemAdapter.notifyDataSetChanged()
                binding.recyclerView.visibility = View.VISIBLE
                binding.txtNotmacth.visibility = View.GONE
            }else{
                productFilter.clear()
                productFilter.addAll(productList)
                filter = productFilter.filter {
                    it.category == category.category
                }
                Log.d("TAG","Product macth $filter")
                isFilterAll = false
                var categoryMatch = filter.any()
                if (categoryMatch){
                    productFilter.clear()
                    productFilter.addAll(filter)
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.progresbar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    isFilterAll = false
                    binding.txtNotmacth.visibility = View.GONE
                    itemAdapter.notifyDataSetChanged()
                    Log.d("TAG","Category match")
                }else{
                    binding.recyclerView.visibility = View.GONE
                    binding.txtNotmacth.visibility = View.VISIBLE
                }
            }



        }

        if (viewModel.isInHome.value == null){
            viewModel.isInHome.value = true

        }


        if (viewModel.isInHome.value == true) {
            readDatabase()
            addCategory()
            viewModel.isInHome.value = false

        }
        if (viewModel.iscreateProduct.value == true){
            productList.clear()
            readDatabase()
            viewModel.iscreateProduct.value = false
        }





        return binding.root
    }



    private fun addCategory() {
        categoryList.add(CategoryData("All",R.drawable.html))
        categoryList.add(CategoryData("Handphone",R.drawable.pngegg))
        categoryList.add(CategoryData("Clothes",R.drawable.nikecloth))
        categoryList.add(CategoryData("Furniture",R.drawable.furniture))
        categoryList.add(CategoryData("Shoes",R.drawable.shoes))
        categoryList.add(CategoryData("Pants",R.drawable.pants))
        categoryList.add(CategoryData("Computer",R.drawable.computer))
        categoryList.add(CategoryData("Electronic",R.drawable.electronic))
    }

    fun readDatabase(){
        db = FirebaseFirestore.getInstance()
        db.collection("product").
                addSnapshotListener(object :EventListener<QuerySnapshot>{
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {
                        if (error != null){
                            Log.e("MainActivity",error.message.toString())
                            return
                        }
                        for (dc:DocumentChange in value?.documentChanges!!){
                            if (dc.type == DocumentChange.Type.ADDED){
                                productList.add(dc.document.toObject(ItemData::class.java))
                                productFilter.addAll(productList)
                                productFilter.clear()
                                productFilter.addAll(productList)
                            }
                        }
                        itemAdapter.notifyDataSetChanged()
                    }

                })

    }

}