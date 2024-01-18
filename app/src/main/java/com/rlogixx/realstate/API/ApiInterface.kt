package com.rlogixx.realstate.API


import com.rlogixx.realstate.Property.AdapterItem
import com.rlogixx.realstate.Property.FlatData
import com.rlogixx.realstate.Property.FlatDataItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface  {
    @GET("propertydata")
    fun getPropertyData(): Call<List<FlatDataItem>>
    //fun getData() : Call<List<FlatDataItem>>
}