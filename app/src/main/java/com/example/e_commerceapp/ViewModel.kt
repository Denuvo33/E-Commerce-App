package com.example.e_commerceapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModel : ViewModel() {

    val isInHome : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val iscreateProduct : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }


    private val _amount = MutableLiveData(false)
    val amount:LiveData<Boolean> =_amount

    fun addAmount(newAmount:Boolean){
        _amount.value = newAmount
    }


}