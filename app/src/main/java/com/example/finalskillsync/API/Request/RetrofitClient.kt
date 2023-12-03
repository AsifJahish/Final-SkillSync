package com.example.finalskillsync.API.Request

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
  private const val BASE_URL = "http://www.boredapi.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: RecipeAPIService = retrofit.create(RecipeAPIService::class.java)

}
