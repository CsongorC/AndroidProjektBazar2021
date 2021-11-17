package com.example.marketplace.api

import com.example.marketplace.model.LoginRequest
import com.example.marketplace.model.LoginResponse
import com.example.marketplace.model.ProductResponse
import com.example.marketplace.utils.Constants
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProducts(@Header("token") token: String): ProductResponse
}