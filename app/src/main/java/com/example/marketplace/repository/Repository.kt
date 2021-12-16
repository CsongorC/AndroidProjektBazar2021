package com.example.marketplace.repository

import com.example.marketplace.api.RetrofitInstance
import com.example.marketplace.model.*
import com.example.marketplace.viewmodels.ProductDataStorage
import retrofit2.http.Part

class Repository {
    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitInstance.api.login(request)
    }

    suspend fun getProducts(token: String): ProductResponse {
        return RetrofitInstance.api.getProducts(token,"100")
    }

    suspend fun register(request: RegisterRequest): RegisterResponse {
        return RetrofitInstance.api.register(request)
    }

    suspend fun getUserInfo(username: String): UserInfoResponse{
        return RetrofitInstance.api.getUserInfo(username)
    }

    suspend fun removeProduct(token: String, product_id: String): ProductRemoveResponse{
        return RetrofitInstance.api.removeProduct(token, product_id)
    }

    suspend fun addProduct(token: String,
                           title : String,
                           description : String,
                           price_per_unit : String,
                           units : String,
                           is_active : Boolean,
                           rating : Double,
                           amount_type : String,
                           price_type : String): ProductAddResponse{
        return RetrofitInstance.api.addProduct(token, title , description, price_per_unit, units, is_active, rating, amount_type, price_type)
    }

    suspend fun getOrders(token: String): OrderResponse {
        return RetrofitInstance.api.getOrders(token,"100")
    }

    suspend fun addOrder(  token: String,
                           title : String,
                           description : String,
                           price_per_unit : String,
                           units : String,
                           status : String,
                           owner_username : String,
                           messages : String): OrderAddResponse{
        return RetrofitInstance.api.addOrder(token, title , description, price_per_unit, units, status, owner_username, messages)
    }

    suspend fun removeOrder(  token: String, order_id: String): OrderRemoveResponse{
        return RetrofitInstance.api.removeOrder(token, order_id)
    }

    suspend fun updateOrder(token: String, order_id: String, updateOrderRequest : OrderUpdateRequest): OrderUpdateResponse{
        return RetrofitInstance.api.updateOrder(token, order_id, updateOrderRequest)
    }
}