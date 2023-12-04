package com.example.finalskillsync.API.Request

import com.example.finalskillsync.API.Model.AllMemsData
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("get_memes")
    suspend fun getMemes() : Response<AllMemsData>
}