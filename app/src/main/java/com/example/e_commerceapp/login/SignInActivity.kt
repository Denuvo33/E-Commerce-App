package com.example.e_commerceapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.e_commerceapp.MainActivity
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : AppCompatActivity() {

    lateinit var binding:ActivitySignInBinding
    lateinit var auth: FirebaseAuth
    lateinit var database:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.btnLogin.setOnClickListener {
            val email = binding.edtemail.text.toString()
            val password = binding.edtpass.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()){
               
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

                    if (it.isSuccessful){
                        Toast.makeText(this,"Masuk", Toast.LENGTH_SHORT).show()
                        binding.inputpass.isErrorEnabled = false
                        binding.inputemail.isErrorEnabled = false
                        loadData()
                    } else {
                        Toast.makeText(this,"Login Failed", Toast.LENGTH_SHORT).show()
                    }

                }
            } else if (email.isEmpty()){
                binding.inputemail.isErrorEnabled = true
                binding.inputemail.error = "White space is not allowed"
            }
            else if (password.isEmpty()){
                binding.inputpass.isErrorEnabled = true
                binding.inputemail.isErrorEnabled = false
                binding.inputpass.error = "White space is not allowed"

            }
            else {
                Toast.makeText(this,"Empty field not allowed!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadData() {
        database = FirebaseDatabase.getInstance().getReference(auth.currentUser!!.uid)
        database.child(auth.currentUser!!.uid).get().addOnSuccessListener {
            if (it.exists()){
                val username = it.child("username").value.toString()
                val intent = Intent(this,MainActivity::class.java)
                intent.putExtra("username",username)
                startActivity(intent)
                finish()

            }
        }.addOnFailureListener {
            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
        }
    }


}
