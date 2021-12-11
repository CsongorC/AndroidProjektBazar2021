package com.example.marketplace.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplace.MyApplication
import com.example.marketplace.model.User
import com.example.marketplace.repository.Repository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: Repository) : ViewModel() {

    init{
        Log.d("xxx", "UserViewModel constructor - username: ${ProductDataStorage.loginUser.username}")
        getInfo()
    }

    fun getInfo() {
        viewModelScope.launch {
            try {
                val result = repository.getUserInfo(ProductDataStorage.loginUser.username)
                ProductDataStorage.loginUser.phone_number=result.data[0].phone_number.toString()
                ProductDataStorage.loginUser.email=result.data[0].email
                Log.d("xxx", "UserViewModel launch()-----------------------------------------------------------------------")
            }catch(e: Exception){
                Log.d("xxx", "UserViewModel exception: $e")
            }
        }
    }
}