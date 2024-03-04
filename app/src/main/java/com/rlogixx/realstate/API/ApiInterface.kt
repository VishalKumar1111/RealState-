package com.rlogixx.realstate.API


import com.google.gson.JsonObject
import com.rlogixx.realstate.Login.LoginRequest
import com.rlogixx.realstate.Login.LoginResponse
import com.rlogixx.realstate.NewProperty.PropertyData
import com.rlogixx.realstate.Property.FlatDataItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface  {
    @GET("propertydata")
    fun getPropertyData(): Call<List<FlatDataItem>>
    //fun getData() : Call<List<FlatDataItem>>

    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("property/add")
    fun addProperty(@Body propertyData: PropertyData): Call<JsonObject> // Use JsonObject or a custom response model

}