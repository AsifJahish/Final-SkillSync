package com.example.finalskillsync.API.DoRequest

import com.example.finalskillsync.API.Request.ApiInterface
import com.example.finalskillsync.API.Request.Util
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstanceDo {
    val api: DoApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(UtilDo.UrlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DoApiInterface::class.java)
    }
}