package com.example.marketplace.api

import com.example.marketplace.model.*
import com.example.marketplace.utils.Constants
import retrofit2.http.*

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProducts(@Header("token") token: String): ProductResponse

    @Multipart
    @POST(Constants.REGISTER_URL)
    suspend fun register(@Part("username") username: String,
                         @Part("password") password: String,
                         @Part("email") email: String) : RegisterResponse
}