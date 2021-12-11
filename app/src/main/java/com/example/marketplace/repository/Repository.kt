package com.example.marketplace.repository

import com.example.marketplace.api.RetrofitInstance
import com.example.marketplace.model.*

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
}