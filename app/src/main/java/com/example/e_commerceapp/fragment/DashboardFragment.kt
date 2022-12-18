package com.example.e_commerceapp.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.R
import com.example.e_commerceapp.ViewModel
import com.example.e_commerceapp.adapter.ItemAdapter
import com.example.e_commerceapp.adapter.ItemData
import com.example.e_commerceapp.databinding.FragmentDashboardBinding
import com.example.e_commerceapp.myproductholder.MyProductAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*


class DashboardFragment : Fragment() {

    lateinit var binding: FragmentDashboardBinding
    lateinit var auth:FirebaseAuth
    lateinit var recyclerView: RecyclerView
    var productList = ArrayList<ItemData>()
    lateinit var itemAdapter: MyProductAdapter
    lateinit var db: FirebaseFirestore

    var addPending = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val viewModel = ViewModelProvider(requireActivity()).get(ViewModel::class.java)
        binding = FragmentDashboardBinding.inflate(layoutInflater)
        val sp : SharedPreferences = this.requireActivity().getSharedPreferences("sharedpref",Context.MODE_PRIVATE)
        auth = FirebaseAuth.getInstance()
        val data = arguments
        var amountPending = 0
        var amountExpress = 0
        val edit = sp.edit()
        productList = arrayListOf()
        binding.txtemail.text = auth.currentUser!!.email
        binding.txtusername.text = data!!.get("name").toString()
        recyclerView = binding.myrecycler
        recyclerView.layoutManager = GridLayoutManager(context,2)
        itemAdapter = MyProductAdapter(productList)
        recyclerView.adapter = itemAdapter
        readDatabase()
        itemAdapter.onDelete = {
            Log.d("TAG","Doc id is = ${db.collection(auth.currentUser!!.uid).document().id}")
        }
        addPending = data!!.getBoolean("pending",false)

        amountPending = sp.getInt("amountt",0)
        if (addPending == true){
            Log.d("TAG","add Pending is true")
            amountPending++
            Log.d("TAG","Qty pending1 is ${amountPending.toString()}")
            data.putBoolean("pending",false)
            binding.txtpending.visibility = View.VISIBLE
            edit.putInt("amountt",amountPending)
            edit.apply()
        }else{
            Log.d("TAG","add Pending is false")
        }
        Log.d("TAG","Qty pending is ${amountPending.toString()}")
        if (amountPending == 0){
            binding.txtpending.visibility = View.GONE
        }else{
            binding.txtpending.visibility = View.VISIBLE
        }
        if (amountExpress == 0){
            binding.txtexpress.visibility = View.GONE
        }else{
            binding.txtexpress.visibility = View.VISIBLE
        }
        binding.txtpending.text = amountPending.toString()





        return binding.root



    }


    fun readDatabase(){
        db = FirebaseFirestore.getInstance()
        db.collection(auth.currentUser!!.uid).
        addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ) {
                if (error != null){
                    Log.e("MainActivity",error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!){
                    if (dc.type == DocumentChange.Type.ADDED){
                        productList.add(dc.document.toObject(ItemData::class.java))
                    }
                }
                itemAdapter.notifyDataSetChanged()
            }

        })

    }


}