package com.example.e_commerceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceapp.adapter.ItemAdapter
import com.example.e_commerceapp.databinding.ActivityMainBinding
import com.example.e_commerceapp.fragment.CreateFragment
import com.example.e_commerceapp.fragment.DashboardFragment
import com.example.e_commerceapp.fragment.HomeFragment
import com.example.e_commerceapp.fragment.WhislistFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    lateinit var database: DatabaseReference
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        val homeFragment = HomeFragment()
        val createFragment = CreateFragment()
        val whislistFragment = WhislistFragment()
        val dashboardFragment = DashboardFragment()
        currentFragment(homeFragment)

        loadData(dashboardFragment)
        var pending = intent.getBooleanExtra("??",false)
        if (pending == true){
            Log.d("TAG","Intent is ${pending.toString()}")
            val bundle = Bundle()
            dashboardFragment.arguments = bundle
            bundle.putBoolean("pending",pending)
        }


        val bottomNav :BottomNavigationView = findViewById(R.id.btmNav)
        bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.home -> currentFragment(homeFragment)
                R.id.create -> currentFragment(createFragment)
                R.id.whislist -> currentFragment(whislistFragment)
                R.id.dashboard -> currentFragment(dashboardFragment)
            }
            true
        }


    }


    private fun loadData(fragment: Fragment) {
        val username = intent.getStringExtra("username")
        val bundle = Bundle()
        fragment.arguments = bundle
        bundle.putString("name",username)
    }


    private fun currentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout,fragment)
            commit()
        }
    }
}