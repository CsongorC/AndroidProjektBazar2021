package com.example.marketplace.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplace.MyApplication
import com.example.marketplace.model.Image
import com.example.marketplace.model.Product
import com.example.marketplace.repository.Repository
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: Repository) : ViewModel() {

    var code : String = ""

    init{
        Log.d("xxx", "ProductViewModel - Token: ${MyApplication.token}")
        Log.d("xxx", "ProductViewModel - product_id: ${ProductDataStorage.my_product_id}")
    }

    suspend fun deleteProduct() {
            try {
                val result =
                    repository.removeProduct(MyApplication.token, ProductDataStorage.my_product_id)
                code = result.message
                Log.d("xxx", "ProductViewModel - #remove:  ${code}")
            }catch(e: Exception){
                Log.d("xxx", "ProductViewModel - #remove: ${e.toString()}")
            }
        }
    }