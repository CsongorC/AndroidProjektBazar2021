package com.example.marketplace.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marketplace.MyApplication
import com.example.marketplace.model.LoginRequest
import com.example.marketplace.model.RegisterRequest
import com.example.marketplace.model.User
import com.example.marketplace.repository.Repository
import com.google.android.material.snackbar.Snackbar

class RegisterViewModel(val context: Context, val repository: Repository) : ViewModel() {
    var token: MutableLiveData<String> = MutableLiveData()

    var user = MutableLiveData<User>()

    init {
        user.value = User()
    }

    suspend fun register() {
        val request =
            RegisterRequest(username = user.value!!.username, password = user.value!!.password, email = user.value!!.email)
        try {
            val result = repository.register(request)
            if(result.code == 200){
                Toast.makeText(context, "Successful registration. ",Toast.LENGTH_SHORT).show()
            }
            if(result.code == 300){
                Toast.makeText(context, "One of the following username , password ,\n" +
                        "email , phone_number, userImage are either\n" +
                        "empty or missing.",Toast.LENGTH_SHORT).show()
            }
            if(result.code == 301){
                Toast.makeText(context, "Wrong file format. Only jpeg or png are\n" +
                        "allowed.",Toast.LENGTH_SHORT).show()
            }
            if(result.code == 302){
                Toast.makeText(context, "Email incorrect. You need to enter another\n" +
                        "email.",Toast.LENGTH_SHORT).show()
            }
            if(result.code == 303){
                Toast.makeText(context, "Username , email or phone number already\n" +
                        "used.",Toast.LENGTH_SHORT).show()
            }
            Log.d("xxx", "MyApplication - token:  ${MyApplication.token}")
        } catch (e: Exception) {
            Log.d("xxx", "LoginViewModel - exception: ${e.toString()}")
        }
    }
}