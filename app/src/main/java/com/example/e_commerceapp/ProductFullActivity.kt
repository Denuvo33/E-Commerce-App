package com.example.e_commerceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import coil.load
import com.example.e_commerceapp.adapter.ItemData
import com.example.e_commerceapp.databinding.ActivityProductFullBinding
import com.example.e_commerceapp.fragment.DashboardFragment
import com.google.android.material.bottomsheet.BottomSheetDialog

class ProductFullActivity : AppCompatActivity() {

    lateinit var binding:ActivityProductFullBinding
    lateinit var viewModel: com.example.e_commerceapp.ViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductFullBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var product = intent.getParcelableExtra<ItemData>("product")

        if (product != null){
            binding.imgProduct.load(product.url)
            binding.txtDesc.text = product.desc
            binding.txtProductname.text = product.name
            binding.txtProductprice.text = product.price
        }
        binding.btnbuy.setOnClickListener {
            showBottomDialog()
        }

    }

    private fun showBottomDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        var qtyAmount = 1
        bottomSheetDialog.setContentView(R.layout.quantityproduct)
        val inc = bottomSheetDialog.findViewById<ImageButton>(R.id.btnincqty)
        val dec = bottomSheetDialog.findViewById<ImageButton>(R.id.btndecqty)
        val buy = bottomSheetDialog.findViewById<Button>(R.id.btnbuyy)
        val qtyText = bottomSheetDialog.findViewById<TextView>(R.id.qty)
        inc!!.setOnClickListener {
            qtyAmount++
            qtyText!!.text = qtyAmount.toString()
        }
        dec!!.setOnClickListener {
            if (qtyAmount != 1){
                qtyAmount--
                qtyText!!.text = qtyAmount.toString()
            }
        }
        buy!!.setOnClickListener {
            Toast.makeText(this,"Thank you for your purchasing,Your product is currently being packaged by the seller",Toast.LENGTH_SHORT).show()
            val intent = Intent(this,MainActivity::class.java)
            intent.putExtra("??",true)
            startActivity(intent)
            finish()
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }
}