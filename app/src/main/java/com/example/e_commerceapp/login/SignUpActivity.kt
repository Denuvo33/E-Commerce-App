package com.example.e_commerceapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.e_commerceapp.MainActivity
import com.example.e_commerceapp.R
import com.example.e_commerceapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User
import java.lang.Exception

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        Handler().postDelayed({
            if (auth.currentUser != null){
                loadData()
            }
        },2000)

        binding.txtLogin.setOnClickListener {
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btncreate.setOnClickListener {
            val email = binding.edtemail.text.toString()
            val username = binding.edtuser.text.toString()
            val password = binding.edtpass.text.toString()

            if (email.isNotEmpty() && username.isNotEmpty() && password.isNotEmpty()) {


                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {task ->

                    try {
                        if (task.isSuccessful){
                            database = FirebaseDatabase.getInstance().getReference(auth.currentUser!!.uid)
                            val user = com.example.e_commerceapp.User(username)
                            database.child(auth.currentUser!!.uid).setValue(user).addOnCompleteListener {
                                Toast.makeText(this,"Succes Save Data",Toast.LENGTH_SHORT).show()
                                Log.d("TAG","Succes Save Data")
                            }
                            val intent = Intent(this,SignInActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else {
                            binding.inputpass.isErrorEnabled = false
                            binding.inputemail.isErrorEnabled = false
                            binding.inputuser.isErrorEnabled = false
                            Toast.makeText(this,"Something wrong,cant create account", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception){
                        Toast.makeText(this,"Email already used", Toast.LENGTH_SHORT).show()
                    }

                }

            } else if (username.isEmpty()){
                binding.inputuser.isErrorEnabled = true
                binding.inputuser.error = "White space is not allowed"
            } else if (email.isEmpty()){
                binding.inputemail.isErrorEnabled = true
                binding.inputemail.error = "White space is not allowed"
                binding.inputuser.isErrorEnabled = false
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
