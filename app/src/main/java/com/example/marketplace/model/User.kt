package com.example.marketplace.model

import com.squareup.moshi.JsonClass

//import com.google.gson.annotations.SerializedName

data class User(var username: String= "", var password: String="", var email: String="", var phone_number: String="")

@JsonClass(generateAdapter = true)
data class LoginRequest (
    var username: String,
    var password: String
)

@JsonClass(generateAdapter = true)
data class LoginResponse (
    var username: String,
    var email: String,
    var phone_number: Int,
    var token: String,
    var creation_time: Long,
    var refresh_time: Long
)

@JsonClass(generateAdapter = true)
data class RegisterRequest (
    var username: String,
    var password: String,
    var email: String,
    var phone_number: Int
)

@JsonClass(generateAdapter = true)
data class RegisterResponse (
    var code : Int,
    var message : String,
    var creation_time: Long
)

@JsonClass(generateAdapter = true)
data class ActivateResponse (
    var code : Int,
    var message : String,
    var creation_time: Long
)

@JsonClass(generateAdapter = true)
data class Data(
    var username: String,
    var phone_number: Long,
    var email: String,
)

@JsonClass(generateAdapter = true)
data class UserInfoResponse(val code: Int, val data: List<Data>, val timestamp: Long)


@JsonClass(generateAdapter = true)
data class ForgotPasswordRequest(val username: String, val email: String)

@JsonClass(generateAdapter = true)
data class ForgotPasswordResponse(
    val code: Int,
    val message: String,
    val timestamp: Long
)