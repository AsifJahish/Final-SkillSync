package com.example.finalskillsync.Network

import com.example.finalskillsync.Model.Quotes
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("quotes")
    suspend fun getMemes() : Response<List<Quotes>>
}