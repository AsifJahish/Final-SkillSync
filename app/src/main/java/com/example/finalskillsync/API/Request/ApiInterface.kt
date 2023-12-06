package com.example.finalskillsync.API.Request

import com.example.finalskillsync.API.Model.Quotes
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("quotes")
    suspend fun getMemes() : Response<List<Quotes>>
}