package com.example.marketplace.repository

import com.example.marketplace.api.RetrofitInstance
import com.example.marketplace.model.*
import okhttp3.MediaType
import okhttp3.RequestBody

class Repository {
    suspend fun login(request: LoginRequest): LoginResponse {
        return RetrofitInstance.api.login(request)
    }

    suspend fun forgotPassword(request: ForgotPasswordRequest): ForgotPasswordResponse {
        return RetrofitInstance.api.forgotPassword(request)
    }

    suspend fun getProducts(token: String): ProductResponse {
        return RetrofitInstance.api.getProducts(token,"300")
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
        return RetrofitInstance.api.addProduct(
            token,
            formatData(title),
            formatData(description),
            formatData(price_per_unit),
            formatData(units),
            formatData(is_active.toString()),
            formatData(rating.toString()),
            formatData(amount_type),
            formatData(price_type))
    }

    suspend fun updateProduct(token: String, product_id: String, updateProductRequest : ProductUpdateRequest): ProductUpdateResponse{
        return RetrofitInstance.api.updateProduct(token, product_id, updateProductRequest)
    }

    suspend fun getOrders(token: String): OrderResponse {
        return RetrofitInstance.api.getOrders(token,"200")
    }

    suspend fun addOrder(  token: String,
                           title : String,
                           description : String,
                           price_per_unit : String,
                           units : String,
                           status : String,
                           owner_username : String,
                           messages : String): OrderAddResponse{
        return RetrofitInstance.api.addOrder(
            token,
            formatData(title),
            formatData(description),
            formatData(price_per_unit),
            formatData(units),
            formatData(status),
            formatData(owner_username),
            formatData(messages))
    }

    suspend fun removeOrder(  token: String, order_id: String): OrderRemoveResponse{
        return RetrofitInstance.api.removeOrder(token, order_id)
    }

    suspend fun updateOrder(token: String, order_id: String, updateOrderRequest : OrderUpdateRequest): OrderUpdateResponse{
        return RetrofitInstance.api.updateOrder(token, order_id, updateOrderRequest)
    }

    fun formatData(input: String): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), input)
    }
}