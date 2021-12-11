package com.example.marketplace.api

import com.example.marketplace.model.*
import com.example.marketplace.utils.Constants
import retrofit2.http.*

interface MarketApi {
    @POST(Constants.LOGIN_URL)
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET(Constants.GET_PRODUCT_URL)
    suspend fun getProducts(@Header("token") token: String, @Header("limit") limit: String): ProductResponse

    @GET(Constants.USER_INFO_URL)
    suspend fun getUserInfo(@Header("username") username: String): UserInfoResponse

    @POST(Constants.REGISTER_URL)
    suspend fun register(@Body request: RegisterRequest) : RegisterResponse

    @POST(Constants.PRODUCTS_REMOVE_URL)
    suspend fun removeProduct(@Header("token") token: String, @Query("product_id") product_id : String) : ProductRemoveResponse
}