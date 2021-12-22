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

    @Multipart
    @POST(Constants.PRODUCT_ADD)
    suspend fun addProduct(
        @Header("token") token: String,
        @Part("title") title : String,
        @Part("description") description : String,
        @Part("price_per_unit") price_per_unit : String,
        @Part("units") units : String,
        @Part("is_active") is_active : Boolean,
        @Part("rating") rating : Double,
        @Part("amount_type") amount_type : String,
        @Part("price_type") price_type : String
    ) : ProductAddResponse

    @POST(Constants.PRODUCT_UPDATE)
    suspend fun updateProduct(@Header("token") token: String,
                              @Query("product_id") product_id : String,
                              @Body request: ProductUpdateRequest
    ): ProductUpdateResponse

    @GET(Constants.GET_ORDER_URL)
    suspend fun getOrders(@Header("token") token: String, @Header("limit") limit: String): OrderResponse

    @Multipart
    @POST(Constants.ADD_ORDER_URL)
    suspend fun addOrder(@Header("token") token: String,
                         @Part("title") title : String,
                         @Part("description") description: String ,
                         @Part("price_per_unit") price_per_unit : String,
                         @Part("units") units : String,
                         @Part("status") status : String,
                         @Part("owner_username") owner_username : String,
                         @Part("messages") messages : String): OrderAddResponse

    @POST(Constants.REMOVE_ORDER_URL)
    suspend fun removeOrder(@Header("token") token: String,
                            @Query("order_id") order_id : String): OrderRemoveResponse

    @POST(Constants.UPDATE_ORDER_URL)
    suspend fun updateOrder(@Header("token") token: String,
                            @Query("order_id") order_id : String,
                            @Body request: OrderUpdateRequest
                            ): OrderUpdateResponse

    @POST(Constants.FORGOT_PASSWORD_URL)
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): ForgotPasswordResponse
}