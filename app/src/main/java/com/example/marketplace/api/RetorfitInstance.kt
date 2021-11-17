package com.example.marketplace.api

import com.example.marketplace.model.LoginRequest
import com.example.marketplace.model.LoginResponse
import com.example.marketplace.model.ProductResponse
import com.example.marketplace.utils.Constants.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

//    private val retrofit by lazy{
//        Retrofit.Builder()
//            .baseUrl(Constants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }

    val api: MarketApi by lazy{
        retrofit.create(MarketApi :: class.java)
    }


}