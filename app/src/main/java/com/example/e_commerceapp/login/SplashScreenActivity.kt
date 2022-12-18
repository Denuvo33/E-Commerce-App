package com.example.e_commerceapp.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.example.e_commerceapp.MainActivity
import com.example.e_commerceapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SplashScreenActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        auth = FirebaseAuth.getInstance()

        Handler().postDelayed({
            if (auth.currentUser != null){
                loadData()
            }
        },1000)

    }

    private fun loadData() {
        database = FirebaseDatabase.getInstance().getReference(auth.currentUser!!.uid)
        database.child(auth.currentUser!!.uid).get().addOnSuccessListener {
            if (it.exists()){
                val username = it.child("username").value.toString()
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("username",username)
                Log.d("MainActivity","start")
                startActivity(intent)
                finish()

            }
        }.addOnFailureListener {
            Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
            Log.d("MainActivity",it.message.toString())
        }
    }

}