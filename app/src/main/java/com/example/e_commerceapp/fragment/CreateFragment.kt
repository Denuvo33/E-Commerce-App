package com.example.e_commerceapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.e_commerceapp.R
import com.example.e_commerceapp.ViewModel
import com.example.e_commerceapp.databinding.FragmentCreateBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class CreateFragment : Fragment() {

    val categoryList = listOf("Furniture","Handphone","Computer","Clothes","Shoes","Pants","Electronic")
    lateinit var arrayAdapter: ArrayAdapter<String>
    var categoryText = ""
    lateinit var binding: FragmentCreateBinding
    var productCreate:MutableMap<String,Any> = HashMap()
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    override fun onResume() {
        super.onResume()
        //SetDropdownMenu
        arrayAdapter = ArrayAdapter(requireContext(),R.layout.auto_completelist,categoryList)
        binding.autoCompleteTxt.setAdapter(arrayAdapter)
        binding.autoCompleteTxt.setOnItemClickListener { parent, view, position, id ->
            categoryText = parent.getItemAtPosition(position).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        binding = FragmentCreateBinding.inflate(layoutInflater)
        db = FirebaseFirestore.getInstance()
        val viewModel = ViewModelProvider(this).get(ViewModel::class.java)




        binding.btncreate.setOnClickListener {
            val productName = binding.edtname.text.toString()
            val prodcutPrice = binding.edtprice.text.toString()
            val productDesc = binding.edtdesc.text.toString()
            val imageUrl = binding.edturl.text.toString()
            val price = "$$prodcutPrice"

            if (productName.isEmpty()){
                binding.inputname.isErrorEnabled = true
                binding.inputname.error = "No white space allowed"
            }else if (productDesc.isEmpty()){
                binding.inputname.isErrorEnabled = false
                binding.inputdesc.isErrorEnabled = true
                binding.inputdesc.error = "No white space allowed"
            }
            else if (prodcutPrice.isEmpty()){
                binding.inputprice.isErrorEnabled = true
                binding.inputdesc.isErrorEnabled = false
                binding.inputprice.error = "No white space allowed"
            }else if (imageUrl.isEmpty()){
                binding.inputurl.isErrorEnabled = true
                binding.inputurl.error = "No white space allowed"
                binding.inputprice.isErrorEnabled = false
            }else{
                binding.inputurl.isErrorEnabled = false
                productCreate["name"] = productName
                productCreate["price"] = price
                productCreate["url"] = imageUrl
                productCreate["desc"] = productDesc
                productCreate["category"] = categoryText
                binding.layout.visibility = View.GONE
                binding.progresbar.visibility = View.VISIBLE
                binding.edtname.text!!.clear()
                binding.edtdesc.text!!.clear()
                binding.edtprice.text!!.clear()
                binding.edturl.text!!.clear()
                db.collection(auth.currentUser!!.uid).add(productCreate).addOnSuccessListener {
                    Toast.makeText(requireContext(),"Succes Created product",Toast.LENGTH_SHORT).show()
                    binding.layout.visibility = View.VISIBLE
                    binding.progresbar.visibility = View.GONE
                    db.collection("product").add(productCreate).addOnSuccessListener {
                        Log.d("TAG","Succes upload to product")
                    }
                }.addOnFailureListener {
                    Toast.makeText(requireContext(),"Failed $it",Toast.LENGTH_SHORT).show()
                    binding.layout.visibility = View.VISIBLE
                    binding.progresbar.visibility = View.GONE
                }
            }

        }



        return binding.root
    }

}