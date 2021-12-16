package com.example.marketplace.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplace.MyApplication
import com.example.marketplace.model.*
import com.example.marketplace.repository.Repository
import kotlinx.coroutines.launch

class OrderViewModel(private val repository: Repository) : ViewModel() {
    var orders: MutableLiveData<List<Order>> = MutableLiveData()
    var code : String = ""

    init{
        Log.d("xxx", "OrderViewModel constructor - Token: ${MyApplication.token}")
        getOrders()
    }

    fun getOrders() {
        viewModelScope.launch {
            try {
                val result =
                    repository.getOrders(MyApplication.token)
                orders.value = result.orders
                Log.d("xxx", "OrderViewModel - #orders:  ${result.item_count}")
            }catch(e: Exception){
                Log.d("xxx", "OrdersViewMofdel exception: ${e.toString()}")
            }
        }
    }

    fun addOrder(title : String, description : String, price_per_unit : String, unit : String, status : String, owner_username : String, messages : String) {
        viewModelScope.launch {
            try {
                val result =
                    repository.addOrder(MyApplication.token,title, description, price_per_unit, unit, status, owner_username, messages)
                code = result.creation
                Log.d("xxx", "OrderViewModel - #orders:  ${code}")
            }catch(e: Exception){
                Log.d("xxx", "OrdersViewMofdel exception: ${e.toString()}")
            }
        }
    }

    fun removeOrder(order_id : String) {
        viewModelScope.launch {
            try {
                val result =
                    repository.removeOrder(MyApplication.token, order_id)
                code = result.message
                Log.d("xxx", "OrderViewModel - #orders:  ${code}")
            }catch(e: Exception){
                Log.d("xxx", "OrdersViewMofdel exception: ${e.toString()}")
            }
        }
    }

    fun upadteOrder(order_id : String, price_per_unit : String, status : String, title :String) {
        viewModelScope.launch {
            val request =
                OrderUpdateRequest(price_per_unit, status)
            try {
                val result =
                    repository.updateOrder(MyApplication.token, order_id, request)
                code = result.status
                Log.d("xxx", "OrderViewModel - #orders:  ${code}")
            }catch(e: Exception){
                Log.d("xxx", "OrdersViewMofdel exception: ${e.toString()}")
            }
        }
    }
}