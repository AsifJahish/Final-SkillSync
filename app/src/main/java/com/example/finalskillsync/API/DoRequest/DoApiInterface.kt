package com.example.finalskillsync.API.DoRequest

import com.example.finalskillsync.API.DoModel.DoData
import com.example.finalskillsync.API.DoModel.DoItem
import com.example.finalskillsync.API.Model.AllMemsData
import retrofit2.Response
import retrofit2.http.GET

interface DoApiInterface {
    @GET("activity")
    suspend fun getDo(): Response<List<DoItem>>
}