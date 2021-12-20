package com.example.marketplace.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplace.MyApplication
import com.example.marketplace.model.Image
import com.example.marketplace.model.OrderUpdateRequest
import com.example.marketplace.model.Product
import com.example.marketplace.model.ProductUpdateRequest
import com.example.marketplace.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.http.Header
import retrofit2.http.Part

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

    suspend fun addProduct(title : String, description : String, price_per_unit : String, units : String, is_active : Boolean, rating : Double, amount_type : String, price_type : String ) {
        try {
            val result =
                repository.addProduct(MyApplication.token, title, description, price_per_unit, units, is_active, rating, amount_type, price_type)
            code = result.creation
            Log.d("xxx", "ProductViewModel - #add:  ${code}")
        }catch(e: Exception){
            Log.d("xxx", "ProductViewModel - #add: ${e.toString()}")
        }
    }

    fun updateProduct(product_id : String, title : String, price : String, description :String) {
        viewModelScope.launch {
            val request =
                ProductUpdateRequest(price.replace("\"", "").toLong(), description, title)
            try {
                val result =
                    repository.updateProduct(MyApplication.token, product_id, request)
                Log.d("xxx", "ProductViewModel - successful update")
            }catch(e: Exception){
                Log.d("xxx", "ProductViewModel update exception: ${e.toString()}")
            }
        }
    }
}

